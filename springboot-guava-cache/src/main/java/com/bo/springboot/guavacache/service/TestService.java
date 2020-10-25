package com.bo.springboot.guavacache.service;

/**
 * @auther: bo
 * @Date: 2020/10/25 17:38
 * @version:
 * @description:
 */

import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class TestService {

    @Autowired
    private Cache<Long, Optional<Object>> test1;

    @Autowired
    private Cache<Long, Optional<Object>> test2;

    @Autowired
    private Cache<Long, Optional<Object>> test3;

    public String test1(Long id) {
        try {
            String o = (String) test1.get(id, () -> {
                if (id / 2 == 0) {
                    return Optional.empty();
                }
                String s = UUID.randomUUID().toString();
                return Optional.of(s);
            }).orElse(null);
            return o;
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
