package com.bo.springboot.service;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: bo
 * @Date: 2020/11/26 17:22
 * @version:
 * @description:
 */
@Service
@SuppressWarnings("all")
public class RuntimeTaskService {

    @Autowired
    private TaskService taskService;

    public List<Task> queryByGroup(String group) {
        return taskService.createTaskQuery().taskCandidateGroup(group).list();
    }

    public List<Task> queryByName(String name) {
        return taskService.createTaskQuery().taskCandidateUser(name).list();
    }
}
