# Spring Cache 使用

## Spring Cache 简介

在 Spring 3.1 中引入了多 Cache 的支持，在 spring-context 包中定义了`org.springframework.cache.Cache` 和 `org.springframework.cache.CacheManager` 两个接口来统一不同的缓存技术。Cache 接口包含缓存的常用操作：增加、删除、读取等。CacheManager 是 Spring 各种缓存的抽象接口。 Spring 支持的常用 CacheManager 如下：

| CacheManager              | 描述                                                         |
| ------------------------- | ------------------------------------------------------------ |
| SimpleCacheManager        | 使用简单的 Collection 来存储缓存                             |
| ConcurrentMapCacheManager | 使用 java.util.ConcurrentHashMap 来实现缓存                  |
| NoOpCacheManager          | 仅测试用，不会实际存储缓存                                   |
| EhCacheCacheManger        | 使用EhCache作为缓存技术。EhCache 是一个纯 Java 的进程内缓存框架，特点快速、精干，是 Hibernate 中默认的 CacheProvider，也是 Java 领域应用最为广泛的缓存 |
| JCacheCacheManager        | 支持JCache（JSR-107）标准的实现作为缓存技术                  |
| CaffeineCacheManager      | 使用 Caffeine 作为缓存技术。用于取代 Guava 缓存技术。        |
| RedisCacheManager         | 使用Redis作为缓存技术                                        |
| HazelcastCacheManager     | 使用Hazelcast作为缓存技术                                    |
| CompositeCacheManager     | 用于组合 CacheManager，可以从多个 CacheManager 中轮询得到相应的缓存 |

Spring Cache 提供了 @Cacheable 、@CachePut 、@CacheEvict 、@Caching 等注解，在方法上使用。通过注解 Cache 可以实现类似事务一样、缓存逻辑透明的应用到我们的业务代码上，且只需要更少的代码。 核心思想：当我们调用一个方法时会把该方法的参数和返回结果最为一个键值对存放在缓存中，等下次利用同样的参数来调用该方法时将不会再执行，而是直接从缓存中获取结果进行返回。

## Cache注解

### 1.@EnableCaching

开启缓存功能，一般放在启动类上。

### 2.@CacheConfig

当我们需要缓存的地方越来越多，你可以使用@CacheConfig(cacheNames = {"cacheName"})注解在 class 之上来统一指定value的值，这时可省略value，如果你在你的方法依旧写上了value，那么依然以方法的value值为准。

### 3.@Cacheable

根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；如果缓存不存在，则执行方法，并把返回的结果存入缓存中。**一般用在查询方法上**。 查看源码，属性值如下：

| 属性/方法名   | 解释                                             |
| ------------- | ------------------------------------------------ |
| value         | 缓存名，必填，它指定了你的缓存存放在哪块命名空间 |
| cacheNames    | 与 value 差不多，二选一即可                      |
| key           | 可选属性，可以使用 SpEL 标签自定义缓存的key      |
| keyGenerator  | key的生成器。key/keyGenerator二选一使用          |
| cacheManager  | 指定缓存管理器                                   |
| cacheResolver | 指定获取解析器                                   |
| condition     | 条件符合则缓存                                   |
| unless        | 条件符合则不缓存                                 |
| sync          | 是否使用异步模式，默认为false                    |

### 4.@CachePut

使用该注解标志的方法，每次都会执行，并将结果存入指定的缓存中。其他方法可以直接从响应的缓存中读取缓存数据，而不需要再去查询数据库。**一般用在新增方法上**。 查看源码，属性值如下：

| 属性/方法名   | 解释                                             |
| ------------- | ------------------------------------------------ |
| value         | 缓存名，必填，它指定了你的缓存存放在哪块命名空间 |
| cacheNames    | 与 value 差不多，二选一即可                      |
| key           | 可选属性，可以使用 SpEL 标签自定义缓存的key      |
| keyGenerator  | key的生成器。key/keyGenerator二选一使用          |
| cacheManager  | 指定缓存管理器                                   |
| cacheResolver | 指定获取解析器                                   |
| condition     | 条件符合则缓存                                   |
| unless        | 条件符合则不缓存                                 |

### 5.@CacheEvict

使用该注解标志的方法，会清空指定的缓存。**一般用在更新或者删除方法上** 查看源码，属性值如下：

| 属性/方法名      | 解释                                                         |
| ---------------- | ------------------------------------------------------------ |
| value            | 缓存名，必填，它指定了你的缓存存放在哪块命名空间             |
| cacheNames       | 与 value 差不多，二选一即可                                  |
| key              | 可选属性，可以使用 SpEL 标签自定义缓存的key                  |
| keyGenerator     | key的生成器。key/keyGenerator二选一使用                      |
| cacheManager     | 指定缓存管理器                                               |
| cacheResolver    | 指定获取解析器                                               |
| condition        | 条件符合则缓存                                               |
| allEntries       | 是否清空所有缓存，默认为 false。如果指定为 true，则方法调用后将立即清空所有的缓存 |
| beforeInvocation | 是否在方法执行前就清空，默认为 false。如果指定为 true，则在方法执行前就会清空缓存 |

### 6.@Caching

该注解可以实现同一个方法上同时使用多种注解。可从其源码看出：

```
public @interface Caching {

	Cacheable[] cacheable() default {};

	CachePut[] put() default {};

	CacheEvict[] evict() default {};

}
复制代码
```

## Spring Cache 使用

### 1.构建项目，添加依赖

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.9.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>cn.zwqh</groupId>
	<artifactId>spring-boot-cache</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>spring-boot-cache</name>
	<description>spring-boot-cache</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<!-- Spring Cache -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
		<!-- jdbc -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>

		<!-- 热部署模块 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional> <!-- 这个需要为 true 热部署才有效 -->
		</dependency>


		<!-- mysql 数据库驱动. -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>

		<!-- mybaits -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>2.1.0</version>
		</dependency>
		
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

复制代码
```

### 2.application.properties 配置文件

```
#datasource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/db_test?useUnicode=true&characterEncoding=UTF-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=123456
#mybatis
mybatis.mapper-locations=classpath:/mapper/*Mapper.xml
复制代码
```

### 3.实体类

```
public class UserEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237730257103305078L;
	private Long id;
	private String userName;
	private String userSex;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	
}
复制代码
```

### 4.数据层 dao 和 mapper.xml

```
public interface UserDao {
	//mapper.xml方式 
	/**
	 * 获取所有用户
	 * @return
	 */
	List<UserEntity> getAll();
	/**
	 * 根据id获取用户
	 * @return
	 */
	UserEntity getOne(Long id);
	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(UserEntity user);
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(UserEntity user);
	/**
	 * 删除用户
	 * @param id
	 */
	void deleteUser(Long id);
		

}
复制代码
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.4//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.zwqh.springboot.dao.UserDao">
	<resultMap type="cn.zwqh.springboot.model.UserEntity" id="user">
		<id property="id" column="id"/>
		<result property="userName" column="user_name"/>
		<result property="userSex" column="user_sex"/>
	</resultMap>
	<!-- 获取所有用户 -->
	<select id="getAll" resultMap="user">
		select * from t_user
	</select>
	<!-- 根据用户ID获取用户 -->
	<select id="getOne" resultMap="user">
		select * from t_user where id=#{id}
	</select>
	<!-- 新增用户 -->
	<insert id="insertUser" parameterType="cn.zwqh.springboot.model.UserEntity">
		insert into t_user (user_name,user_sex) values(#{userName},#{userSex})
	</insert>
	<!-- 修改用户 -->
	<update id="updateUser" parameterType="cn.zwqh.springboot.model.UserEntity">
		update t_user set user_name=#{userName},user_sex=#{userSex} where id=#{id}
	</update>
	<!-- 删除用户 -->
	<delete id="deleteUser" parameterType="Long">
		delete from t_user where id=#{id}
	</delete>
</mapper>


复制代码
```

### 5.业务代码层接口 Service 和实现类 ServiceImpl

```
public interface UserService {

	/**
	 * 查找所有
	 * @return
	 */
	List<UserEntity> getAll();
	/**
	 * 根据id获取用户
	 * @param id
	 * @return
	 */
	UserEntity getOne(Long id);
	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(UserEntity user);
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUser(UserEntity user);
	
	void deleteAll1();
	
	void deleteAll12();
}

复制代码
@Service
@CacheConfig(cacheNames = {"userCache"})
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Cacheable("userList") // 标志读取缓存操作，如果缓存不存在，则调用目标方法，并将结果放入缓存
	public List<UserEntity> getAll() {
		System.out.println("缓存不存在，执行方法");
		return userDao.getAll();
	}

	@Override
	@Cacheable(cacheNames = { "user" }, key = "#id")//如果缓存存在，直接读取缓存值；如果缓存不存在，则调用目标方法，并将结果放入缓存
	public UserEntity getOne(Long id) {
		System.out.println("缓存不存在，执行方法");
		return userDao.getOne(id);
	}

	@Override
	@CachePut(cacheNames = { "user" }, key = "#user.id")//写入缓存，key为user.id,一般该注解标注在新增方法上
	public void insertUser(UserEntity user) {
		System.out.println("写入缓存");
		userDao.insertUser(user);
	}

	@Override
	@CacheEvict(cacheNames = { "user" }, key = "#user.id")//根据key清除缓存，一般该注解标注在修改和删除方法上
	public void updateUser(UserEntity user) {
		System.out.println("清除缓存");
		userDao.updateUser(user);
	}
	
	@Override
    @CacheEvict(value="userCache",allEntries=true)//方法调用后清空所有缓存
    public void deleteAll1() {
	
	}
	
	@Override
    @CacheEvict(value="userCache",beforeInvocation=true)//方法调用前清空所有缓存
    public void deleteAll2() {

    }

}
复制代码
```

### 6.测试 Controller

```
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 *  查找所有
	 * @return
	 */
	@RequestMapping("/getAll")
	public List<UserEntity> getAll(){
		return userService.getAll(); 
	}
	/**
	 * 根据id获取用户
	 * @return
	 */
	@RequestMapping("/getOne")
	public UserEntity getOne(Long id){
		return userService.getOne(id); 
	}
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@RequestMapping("/insertUser")
	public String insertUser(UserEntity user) {
		userService.insertUser(user);
		return "insert success";
	}	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(UserEntity user) {
		userService.updateUser(user);
		return "update success";
	}
}
复制代码
```

### 7.启动 Cache 功能

```
@SpringBootApplication
@MapperScan("cn.zwqh.springboot.dao")
@EnableCaching //启动 Cache 功能
public class SpringBootCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCacheApplication.class, args);
	}

}

复制代码
```

### 8.数据库及测试数据

数据库和测试数据仍旧用之前的。

### 9.测试

编写单元测试，或者通过访问 `http://127.0.0.1:8080/user/` 加上对应路径和参数。