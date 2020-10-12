package com.bo.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: bo
 * @Date: 2020/10/12 11:05
 * @Version:
 * @Description:
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String getOne() {

        return "hello world";
    }
}
