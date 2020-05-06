package com.bo.springcache.config;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * @Auther: bo
 * @Date: 2020/5/6 21:53
 * @Version:
 * @Description:
 */
public class MyRedisCacheManager extends RedisCacheManager {
    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /**
     * 生成延时时间
     * @param name
     * @param cacheConfig
     * @return
     */
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
