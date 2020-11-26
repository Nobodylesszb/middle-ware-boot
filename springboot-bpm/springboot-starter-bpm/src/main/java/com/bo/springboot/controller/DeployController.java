package com.bo.springboot.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: bo
 * @Date: 2020/11/26 16:26
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/deploy")
public class DeployController {

    @Autowired
    private RepositoryService repositoryService;


    @GetMapping("/FinancialReportProcess")
    public String testFinancialReportProcess() {
        ClassPathResource classPathResource = new ClassPathResource("bpmn/FinancialReportProcess.bpmn20.xml", this.getClass().getClassLoader());
        Deployment deployment = repositoryService.createDeployment()
                .key("test")
                .name("test1")
                .addClasspathResource(classPathResource.getPath())
                .deploy();
        return deployment.getId();
    }
}
