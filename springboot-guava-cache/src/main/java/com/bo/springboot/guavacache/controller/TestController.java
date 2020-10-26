package com.bo.springboot.guavacache.controller;

import com.bo.springboot.guavacache.annotation.InvalidCache;
import com.bo.springboot.guavacache.dto.Test1;
import com.bo.springboot.guavacache.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: bo
 * @Date: 2020/10/25 17:36
 * @version:
 * @description:
 */

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test1/{id}")
    public String test1(@PathVariable(value = "id") Long id) {
        return testService.test1(id);
    }

    /**
     * 更新缓存用切面来做
     *
     * @param id
     * @return
     */
    @PostMapping("/test1/{id}")
    @InvalidCache(name = {"test1", "test2"}, invalid = "id", IncomingObject = Test1.class, IncomingObjectName = "test1")
    public String savaTest1(@PathVariable(value = "id") Long id, @RequestBody Test1 test1) {
        return "已经更新了缓存";
    }
}
