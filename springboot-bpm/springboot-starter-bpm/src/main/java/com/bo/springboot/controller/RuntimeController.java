package com.bo.springboot.controller;

import com.bo.springboot.service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @auther: bo
 * @Date: 2020/11/26 17:03
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/runtime")
public class RuntimeController {

    @Autowired
    private RunService runService;

    @RequestMapping("/{key}")
    public String startProcessInstanceByKey(@PathVariable("key") String key) {
        return runService.startProcessInstanceByKey(key);
    }
}
