SpringBoot 中除了了对常用的关系型数据库提供了优秀的自动化测试以外，对于很多 NoSQL 数据库一样提供了自动化配置的支持，包括：Redis, MongoDB, Elasticsearch, Solr 和 Cassandra。

## 整合redis

Redis是一个速度非常快的非关系型数据库（non-relational database），它可以存储键（key）与5种不同类型的值（value）之间的映射（mapping），可以将存储在内存的键值对数据持久化到硬盘。可以使用复制特性来扩展读性能，还可以使用客户端分片来扩展写性能。

- redis官网
- redis中文社区

### 引入依赖

Spring Boot 提供了对 Redis 集成的组件包：spring-boot-starter-data-redis，spring-boot-starter-data-redis 依赖于spring-data-redis 和 lettuce 。

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
 </dependency>
复制代码
```

### 参数配置

在 application.properties 中加入Redis服务端的相关配置 ：

```
#redis配置
#Redis服务器地址
spring.redis.host=127.0.0.1
#Redis服务器连接端口
spring.redis.port=6379
#Redis数据库索引（默认为0）
spring.redis.database=0  
#连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=50
#连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=3000ms
#连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=20
#连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=2
#连接超时时间（毫秒）
spring.redis.timeout=5000ms
复制代码
```

其中 spring.redis.database 的配置通常使用0即可，Redis 在配置的时候可以设置数据库数量，默认为16，可以理解为数据库的 schema

### 测试访问

通过编写测试用例，举例说明如何访问Redis。

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstSampleApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void test() throws Exception {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("name", "chen");
        Assert.assertEquals("chen", stringRedisTemplate.opsForValue().get("name"));
    }
}
复制代码
```

上面的案例通过自动配置的 `StringRedisTemplate` 对象进行 redis 的对写操作，从对象命名就可注意到支持的是 string 类型，如果有用过 spring-data-redis 的开发者一定熟悉 `RedisTemplate` 接口，StringRedisTemplate 就相当于 `RedisTemplate` 的实现。

除了 String 类型，实战中经常会在 redis 中储存对象，我们就要在储存对象时对对象进行序列化。下面通过一个实例来完成对象的对写操作。

**创建 User 实体**

```
@Data
public class User implements Serializable {

    private String userName;

    private Integer age;
}
复制代码
```

**配置针对对象的RedisTemplate实例**

```
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
 
    /**
     * 采用RedisCacheManager作为缓存管理器
     * @param connectionFactory
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
        return  redisCacheManager;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        //解决键、值序列化问题
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
复制代码
```

**完成了配置工作后，编写测试用例实验效果**

```
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FirstSampleApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void test() throws Exception {
        //保存对象
        User user = new User();
        user.setUserName("chen");
        user.setAge(22);
        redisTemplate.opsForValue().set(user.getUserName(), user);
        log.info("result:{}",redisTemplate.opsForValue().get("chen"));
    }
}
复制代码
```

这样我们就能对对象进行缓存了。但是在对 redis 更深入的了解中，一不小心就踩进坑里去了，下面对 redis 踩的坑做下记录。

## 踩坑记录

### 踩坑1：cacheable注解引发的乱码问题

```
@RestController
@RequestMapping("/chen/user")
@Slf4j
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/hello")
    @Cacheable(value = "redis_key",key = "#name",unless = "#result == null")
    public User hello(@RequestParam("name")String name){
        User user = new User();
        user.setName(name);
        user.setAge(22);
        user.setEmail("chen_ti@outlook.com");
        return user;
    }
}
复制代码
```

在使用 SpringBoot1.x 的时候，通过简单的配置 RedisTemplete 就可以了，升级到 SpringBoot2.0，spring-boot-starter-data-redis 也跟着升起来了，@Cacheable 就出现了乱码的情况，可以通过将上面的配置文件 RedisConfiguration 做如下更改解决 ：

```
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
    @Bean(name="redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))             .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
        return cacheManager;
    }
}
复制代码
```

### 踩坑2：redis 获取缓存异常

报错信息:

```
java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to com.tuhu.twosample.chen.entity.User
复制代码
```

Redis获取缓存异常：java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX。

出现这种异常，我们需要自定义 `ObjectMapper`，设置一些参数，而不是直接使用 Jackson2JsonRedisSerializer 类中黙认的 ObjectMapper，看源代码可以知道，Jackson2JsonRedisSerializer 中的 ObjectMapper 是直接使用new ObjectMapper() 创建的，这样 ObjectMapper 会将 redis 中的字符串反序列化为 java.util.LinkedHashMap类型，导致后续 Spring 对其进行转换成报错。其实我们只要它返回 Object 类型就可以了。

使用以下方法，构建一个 `Jackson2JsonRedisSerializer` 对象，将其注入 RedisCacheManager 即可。

```
	/**
     * 通过自定义配置构建Redis的Json序列化器
     * @return Jackson2JsonRedisSerializer对象
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
复制代码
```

### 踩坑3：类转移路径

**异常打印:**

```
19:32:47 INFO  - Started Application in 10.932 seconds (JVM running for 12.296)
19:32:50 INFO  - get data from redis, key = 10d044f9-0e94-420b-9631-b83f5ca2ed30
19:32:50 WARN  - /market/renewal/homePage/index
org.springframework.data.redis.serializer.SerializationException: Could not read JSON: Could not resolve type id 'com.pa.market.common.util.UserInfoExt' into a subtype of [simple type, class java.lang.Object]: no such class found
 at [Source: [B@641a684c; line: 1, column: 11]; nested exception is com.fasterxml.jackson.databind.exc.InvalidTypeIdException: Could not resolve type id 'com.pa.market.common.util.UserInfoExt' into a subtype of [simple type, class java.lang.Object]: no such class found at [Source: [B@641a684c; line: 1, column: 11]
复制代码
```

**问题原因:**

项目中使用了拦截器，对每个 http 请求进行拦截。通过前端传递过来的 token，去 redis 缓存中获取用户信息UserInfoExt，用户信息是在用户登录的时候存入到 redis 缓存中的。根据获取到的用户信息来判断是否存是登录状态。 所以除白名单外的 url，其他请求都需要进行这个操作。通过日志打印，很明显出现在 UserInfoExt 对象存储到 redis 中序列化和反序列化的操作步骤。

**解决办法:**

```
@Bean
public RedisTemplate<K, V> redisTemplate() {
    RedisTemplate<K, V> redisTemplate = new RedisTemplate<K, V>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
 }
复制代码
```

查看 redis 的 Bean 定义发现，对 key 的序列化使用的是 StringRedisSerializer 系列化，value 值的序列化是 `GenericJackson2JsonRedisSerializer` 的序列化方法。

其中 `GenericJackson2JsonRedisSerializer` 序列化方法会在 redis 中记录类的 @class 信息，如下所示：

```
{
    "@class": "com.pa.market.common.util.UserInfoExt",
    "url": "www.baidu.com",
    "name": "baidu"
}
复制代码
```

"@class": "com.pa.market.common.util.UserInfoExt"，每个对象都会有这个 id 存在（可以通过源码看出为嘛有这个 @class），如果用户一直处在登录状态，是以 com.pa.market.common.util.UserInfoExt 这个路径进行的序列化操作。但是移动了 UserInfoExt 的类路径后，包全名变了。所以会抛出 no such class found 的异常。这样在判断用户是否存在的地方就抛出了异常，故而所有的请求都失败了，已经登录的用户没法进行任何操作。

ok 把踩的坑都记录下来，终于呼出了最后一口气，以后遇到这种坑都能从容的避开了，但是 redis 中的坑还有很多，可能以后还是会轻轻松松的跳进去。

