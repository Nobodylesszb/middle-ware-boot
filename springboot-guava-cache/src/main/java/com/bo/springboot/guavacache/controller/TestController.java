package com.bo.springboot.guavacache.controller;

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
     * @param id
     * @return
     */
    @PostMapping("/test1/{id}")
    public String Savatest1(@PathVariable(value = "id") Long id) {
        return "已经更新了缓存";
    }
}
