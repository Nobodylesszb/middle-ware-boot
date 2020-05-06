package com.bo.springcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Auther: bo
 * @Date: 2020/5/6 21:13
 * @Version:
 * @Description:
 */

@SpringBootApplication
@EnableCaching
public class SpringBootCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheApplication.class, args);
}
}
