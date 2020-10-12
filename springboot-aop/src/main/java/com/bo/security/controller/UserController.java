package com.bo.security.controller;

import com.bo.security.common.aop.ControllerWebLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: bo
 * @Date: 2020/5/7 11:05
 * @Version:
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getOne")
    @ControllerWebLog(name = "查询", intoDb = true)
    public String getOne(Long id, String name) {

        return "1234";
    }
}
