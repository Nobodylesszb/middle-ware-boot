package com.bo.springboot.guavacache.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @auther: bo
 * @Date: 2020/10/24 22:58
 * @version:
 * @description:
 */
@Configuration
public class GuavaConfig {

    @Bean("test1")
    public Cache<Long, Optional<Object>> buildUserInfoDtoCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(2000) // 设置缓存的最大容量
                .expireAfterWrite(30, TimeUnit.MINUTES) // 设置缓存在写入30分钟后失效
                .concurrencyLevel(10) // 设置并发级别为10
                .recordStats() // 开启缓存统计
                .build();
    }

    @Bean("test2")
    public Cache<Long, Optional<Object>> buildStaffDepartmentTreeDTO() {
        return CacheBuilder.newBuilder()
                .maximumSize(2000) // 设置缓存的最大容量
                .expireAfterWrite(30, TimeUnit.MINUTES) // 设置缓存在写入30分钟后失效
                .concurrencyLevel(10) // 设置并发级别为10
                .recordStats() // 开启缓存统计
                .build();
    }

    @Bean("test3")
    public Cache<Long, Optional<Object>> buildStaffDepartmentTreeVO() {
        return CacheBuilder.newBuilder()
                .maximumSize(2000) // 设置缓存的最大容量
                .expireAfterWrite(30, TimeUnit.MINUTES) // 设置缓存在写入30分钟后失效
                .concurrencyLevel(10) // 设置并发级别为10
                .recordStats() // 开启缓存统计
                .build();
    }
}
