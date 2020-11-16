package com.bo.webservice.controller;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther: bo
 * @Date: 2020/11/16 14:45
 * @version:
 * @description:
 */
@Controller
@RequestMapping("/")
public class TestController {

    @GetMapping
    public void test() {
        JaxWsDynamicClientFactory dcflient = JaxWsDynamicClientFactory.newInstance();

        Client client = dcflient.createClient("http://localhost:8080/ws/user?wsdl");
        try {
            Object[] objects = client.invoke("getUserById", "1");
            System.out.println("getUserById 调用结果：" + objects[0].toString());

            Object[] objectall = client.invoke("getUsers");
            System.out.println("getUsers调用部分结果：" + objectall[0].toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
