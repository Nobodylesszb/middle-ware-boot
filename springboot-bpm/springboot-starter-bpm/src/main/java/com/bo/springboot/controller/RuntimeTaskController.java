package com.bo.springboot.controller;

import com.bo.springboot.service.RuntimeTaskService;
import com.bo.springboot.utils.RetResult;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther: bo
 * @Date: 2020/11/26 17:26
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/runtimeTask")
public class RuntimeTaskController {

    @Autowired
    private RuntimeTaskService runtimeTaskService;

    /**
     * @param group
     * @return
     */
    @GetMapping("/group/{group}")
    public RetResult queryByGroup(@PathVariable("group") String group) {
        List<Task> tasks = runtimeTaskService.queryByGroup(group);
        return new RetResult(200, tasks);
    }

    @GetMapping("/name/{name}")
    public RetResult queryByName(@PathVariable("name") String name) {
        List<Task> tasks = runtimeTaskService.queryByName(name);
        return new RetResult(200, tasks);
    }
}
