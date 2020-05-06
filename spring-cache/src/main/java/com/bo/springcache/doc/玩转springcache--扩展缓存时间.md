#### 前言

在上篇文章讲解整合分布式缓存`Redis`时埋下了一个伏笔：如何让我们的缓存注解支持**自定义TTL失效时间**呢？

这篇文章本可以不用写，因为其实基于`Redis`的`RedisCacheManager`它本身天生就是能够针对不同的`Cache`配置不同的TTL的。但是我发现有的小伙伴觉得使用得还是不太方便，**希望能在使用注解的时候直接控制失效时间**，为了帮助解决小伙伴的这个`困惑`，这就是我书写本文的目的~

#### Spring Cache与失效时间TTL

首先此处我有必要再次强调一点：`Spring Cache`抽象本省是并**不支持**`Expire`失效时间的设定的，我粗暴的把它归为了`Spring Cache`抽象的一个设计上的bug，可参考文章：[【小家Spring】玩转Spring Cache — @Cacheable/@CachePut/@CacheEvict注解的原理深度剖析和使用](https://blog.csdn.net/f641385712/article/details/94570960)

**若想在缓存注解上指定失效时间，必须具备如下两个基本条件：**

1. 缓存实现产品支持`Expire`失效时间（Ehcache、Redis等几乎所有第三方实现都支持）
2. `xxxCacheManager`管理的`xxxCache`必须扩展了`Expire`的实现

因为缓存的k-v键值对具有**自动失效的特性**实在太重要和太实用了，所以虽然`org.springframework.cache.Cache`它没有实现`Expire`，但好在第三方产品对Spring缓存标准实现的时候，大都实现了这个重要的**失效策略**，比如典型例子：`RedisCache`。

本文以最为常用的`Redis`缓存为例，介绍两种控制缓存失效时间的方式。

## 实现Cache失效时间的两种通用方式

接下来就以`Redis Cache`为例，介绍两种常用的、通用的管理缓存失效时间的方式。

#### 方式一：使用源生的`RedisCacheManager`进行集中式控制

由于控制key的失效时间这一块非常的实用和重要，所以其实`Spring Data Redis`工程早就给与了支持（不管是1.x版本还是2.x版本）。因此话不多说，直接给个例子就非常清晰明了：

1、准备Cache配置：

```java
@EnableCaching // 使用了CacheManager，别忘了开启它  否则无效
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1)) // 默认没有特殊指定的
                .computePrefixWith(cacheName -> "caching:" + cacheName);

        // 针对不同cacheName，设置不同的过期时间
        Map<String, RedisCacheConfiguration> initialCacheConfiguration = new HashMap<String, RedisCacheConfiguration>() {{
            put("demoCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1))); //1小时
            put("demoCar", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10))); // 10分钟
            // ...
        }};

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(defaultCacheConfig) // 默认配置（强烈建议配置上）。  比如动态创建出来的都会走此默认配置
                .withInitialCacheConfigurations(initialCacheConfiguration) // 不同cache的个性化配置
                .build();
        return redisCacheManager;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("10.102.132.150");
        configuration.setPort(6379);
        configuration.setDatabase(0);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        return factory;
    }

    @Bean
    public RedisTemplate<String, String> stringRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

}
```

使用示例：

```java
@Service
public class CacheDemoServiceImpl implements CacheDemoService {


    @Caching(cacheable = {
            @Cacheable(cacheNames = "demoCache", key = "#id + 0"),
            @Cacheable(cacheNames = "demoCar", key = "#id + 10"),
            @Cacheable(cacheNames = "demoFsx", key = "#id + 100")
    })
    @Override
    public Object getFromDB(Integer id) {
        System.out.println("模拟去db查询~~~" + id);
        return "hello cache...";
    }
}
```

运行单元测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, CacheConfig.class})
public class TestSpringBean {

    @Autowired
    private CacheDemoService cacheDemoService;
    @Autowired
    private CacheManager cacheManager;


    @Test
    public void test1() {
        cacheDemoService.getFromDB(1);
        cacheDemoService.getFromDB(1);

        System.out.println("----------验证缓存是否生效----------");
        Cache cache = cacheManager.getCache("demoCache");
        System.out.println(cache);
        System.out.println(cache.get(1, String.class));
    }
}
```

打印结果如下：

```javascript
模拟去db查询~~~1
----------验证缓存是否生效----------
org.springframework.data.redis.cache.RedisCache@721eb7df
hello cache...
```

**缓存生效**。去Redis服务端查看对应的key情况：

![image-20200506221056366](https://gitee.com/nobodylesszb/upic/raw/pic/upload/pics/1588774256-image-20200506221056366.png)

>  眼睛尖的小伙伴可能发现了，只有最后一个key前面有`caching`前缀，其余两个木有，这是为何呢？ 这是我故意留出来的一个小问题，留给小伙伴们自行思考~ 

由此可见，通过`RedisCacheManager`完成了对不同的`Cache`进行了失效时间的定制化配置，达到了我们的目的。

##### 小细节

针对如上的配置，总结如下两点小细节使时需要注意：

1. 即使禁用前缀`disableKeyPrefix()`，也是不会影响对应CacheName的TTL（因为**TTL针对的是Cache，而不是key**）
2. 每个CacheName都可以对应一个`RedisCacheConfiguration`（它里面有众多属性都可以个性化），若没配置的（比如动态生成的）都走默认配置

>  Spring提供的在`RedisCacheManager`来统一管理Cache的TTL，这种集中式的管理其实是我赞同的方式，若让他分散在各个缓存注解上，反而非常不利于后期的维护管理~~~因此这种方式我也是推荐的 

#### 方式二：自定义cacheNames方式

虽然我觉得方案一已经能够满足我们需求了，但是广大小伙伴还是觉得使用起来不太自由，毕竟大多数Cache都希望是通过在注解指定CacheNames让其`自动生成`就行（其实提前追备好有助于提升初次访问的性能）。但是为了便用性摆第一位的话，那就应广大小伙伴的要求，写出本例**供以参考**：

**其实最终我们期望的使用方式如下：**

```javascript
@Cacheable(cacheNames = "demoCache#3600", key = "#id + 0"),
```

通过`#`分隔，后面部分表示此`Cache`的TTL（单位：秒）

为了实现这个效果，其实并不难，只需要对`RedisCacheManager`稍稍的改造一下即可达到目的：

```javascript
public class MyRedisCacheManager extends RedisCacheManager {

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        if (array.length > 1) { // 解析TTL
            long ttl = Long.parseLong(array[1]);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl)); // 注意单位我此处用的是秒，而非毫秒
        }
        return super.createRedisCache(name, cacheConfig);
    }

}
```

使用我自定义的`MyRedisCacheManager`配置CacheConfig如下：

```javascript
@EnableCaching // 使用了CacheManager，别忘了开启它  否则无效
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .computePrefixWith(cacheName -> "caching:" + cacheName);

        MyRedisCacheManager redisCacheManager = new MyRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory()), defaultCacheConfig);
        return redisCacheManager;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("10.102.132.150");
        configuration.setPort(6379);
        configuration.setDatabase(0);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        return factory;
    }

    @Bean
    public RedisTemplate<String, String> stringRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

}
```

**使用示例如下：**

```javascript
@Service
public class CacheDemoServiceImpl implements CacheDemoService {

    @Cacheable(cacheNames = {
            "demoCache#3600", "demoCar#600", "demoFsx"
    }, key = "#id")
    @Override
    public Object getFromDB(Integer id) {
        System.out.println("模拟去db查询~~~" + id);
        return "hello cache...";
    }
}
```

打印结果：

```javascript
模拟去db查询~~~1
----------验证缓存是否生效----------
org.springframework.data.redis.cache.RedisCache@53f4c1e6
hello cache...
```

缓存生效。**Redis Server里查到缓存结果如图（TTL都分别生效了）：** 

![img](https://ask.qcloudimg.com/http-save/yehe-6158873/6ugucqhf30.png?imageView2/2/w/1620)

>  说明：`demoFsx`没有指定TTL，所以走了默认值`ttl=1天` 

##### 小细节

1. 同样的，禁用前缀并不影响它的TTL的生效与否
2. 若在`CacheManager`里**已经配置了**`Cache`对应的TTL配置，那就以`CacheManager`里配置的为准
3. 若**多个方法里配置了同一个CacheName**，`TTL`以第一个执行的生成Cache的方法配置的为准

>  总之一个原则：TTL是和Cache绑定的，且是在`Cache`在首次被初始化的时候就被指定好了 

**关于此方案，其实还可以扩展一下，比如可以扩展成可配置的如下：**

```javascript
@Cacheable(cacheNames = "demoCache#${cache.timeout:1800}",key = "#id")
```

方案提出来，实现此处我就不写了，因为还是比较容易实现的。

#### TTL能精确到key吗？

据我了解，这是很多小伙伴都想问的一个问题，但其实我一直不明白为何有非常多的小伙伴会有这种呼声呢？

很多小伙伴是希望把TTL写在key上，形如这样书写：

```javascript
@Cacheable(cacheNames = "demoCache",key = "#id_3600")
```

其实这么想的小伙伴，我觉得根本原因是不太能理解`cacheName`和`Redis`的key的关系导致的（本文以Redis为例~）

**我此处不直接解答这个问题**，但我对此额外抛出3个问题，相信答案就不攻自破了：

1. 为何**同一个Cache**下管理的key你需要不同的TTL？？？（这种设计本身就不合理吧）
2. 在不禁用前缀的情况下，`cacheName`默认都会反映到key上。因此即使你有这种特殊需求，你也可以通过定义**特殊的CacheName**来实现
3. 若你真想控制到key这种细粒度，我只能说：实现成本太高了且会打破一定的封装性，后续扩展受限

**综合来说，不管从场景上还是技术上，我都是极力不推荐这种行为的。**

#### 总结

本文主要介绍了让缓存注解支持`TTL`失效时间，提供的两种方式都可以用在生产环境中。合理的使用、控制失效时间，能让你的应用更加的高效，缓存利用得更合理。

**另外关于Spring缓存其实还有一个重要知识点：`缓存即将过期时主动刷新缓存`**： 因为缓存失效后，就会有一些请求会打到DB上，这段时间**如果是高并发**的话DB压力就很大（sync=true可以有一定的缓解作用），DB就很危险，容易造成雪崩。 因此我们是期望在**缓存即将过期的某一时间点**，后台`主动`去更新缓存以确保前端请求的缓存命中率。关于这部分的实现，只有在高并发系统下才有需求，有兴趣和有需要的小伙伴可往这方面考虑一把~