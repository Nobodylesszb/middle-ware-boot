package com.bo.security.controller;

import com.bo.security.common.aop.ControllerWebLog;
import com.bo.security.config.UserProperties;
import com.bo.security.domain.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserProperties test;

    @Autowired
    private MyBean myBean1;
    @Autowired
    private MyBean myBean2;

    @GetMapping("/getOne")
    @ControllerWebLog(name = "查询", intoDb = true)
    public String getOne(Long id, String name) {
        String apiToken = test.getJenkinsConnectionInfo().getApiToken();
        return apiToken;
    }

    @GetMapping
    public String get() {
        String s = myBean1.toString();
        String s1 = myBean2.toString();
        System.out.println(s + s1);
        return "true";
    }
}
