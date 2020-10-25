package com.bo.springboot.guavacache.controller;

import com.bo.springboot.guavacache.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
