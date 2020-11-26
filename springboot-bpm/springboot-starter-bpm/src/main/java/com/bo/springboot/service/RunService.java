package com.bo.springboot.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther: bo
 * @Date: 2020/11/26 17:01
 * @version:
 * @description:
 */
@Service
@SuppressWarnings("all")
public class RunService {

    @Autowired
    private RuntimeService runtimeService;

    public String startProcessInstanceByKey(String key) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        return processInstance.getId();
    }

}
