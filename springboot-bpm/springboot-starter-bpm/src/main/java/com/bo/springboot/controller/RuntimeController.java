package com.bo.springboot.controller;

import com.bo.springboot.service.RunService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @auther: bo
 * @Date: 2020/11/26 17:03
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/runtime")
@SuppressWarnings("all")
public class RuntimeController {

    @Autowired
    private RunService runService;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping("/{key}")
    @Transactional(rollbackFor = Exception.class)
    public String startProcessInstanceByKey(@PathVariable("key") String key) {
        String s = runService.startProcessInstanceByKey(key);
//        runtimeService.setVariable(s, "users", "test,test2,test3");
        List<Task> list = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(s).list();
        if (CollectionUtil.isNotEmpty(list)) {
            System.out.println(list);
            for (Task task : list) {
                taskService.setVariable(task.getId(), "候选人", "bo,ning,li");
            }
        }
        return s;
    }

    @GetMapping("/info")
    public String getTaskInfobyKey(String key) {
        System.out.println(key);
        List<Task> list = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(key).list();
        System.out.println(list);
        return "sucess";
    }

    @GetMapping("/two")
    public String getVariblebyTaskKey(String key, String name) {
        String resean = (String) taskService.getVariable(key, name);
        return resean;
    }
}
