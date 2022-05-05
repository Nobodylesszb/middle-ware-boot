package com.bo.redisson.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    @Resource
    private RedissonClient redissonClient;

    @GetMapping(value = "/test")
    public String lockDemo() {
        RLock test = redissonClient.getLock("test");
        try {
            test.lock();
            //模拟业务操作耗时
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            test.unlock();
        }
        return "success";
    }
}
