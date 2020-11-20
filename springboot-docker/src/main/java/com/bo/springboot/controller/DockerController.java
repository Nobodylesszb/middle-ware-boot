package com.bo.springboot.controller;

import com.bo.springboot.config.EnvConfig;
import com.bo.springboot.dao.CarDao;
import com.bo.springboot.dao.UserDao;
import com.bo.springboot.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

/**
 * @auther: bo
 * @Date: 2020/11/20 15:32
 * @version:
 * @description:
 */
public class DockerController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CarDao carDao;
    @Autowired
    private EnvConfig envConfig;

    @GetMapping(value = "/getEnv")
    private String getEnv() {
        System.out.println(envConfig.getName());
        return envConfig.getName();
    }

    @GetMapping(value = "/insert-mongodb")
    public String insertMongoDB() {
        Car car = new Car();
        car.setId(new Random().nextInt(15000000));
        String number = String.valueOf(System.currentTimeMillis());
        car.setNumber(number);
        carDao.save(car);
        return "this is insert-mongodb";
    }

    @GetMapping(value = "/insert-mysql")
    public String insertMySQL() {
        userDao.insert();
        return "this is insert-mysql";
    }

    @GetMapping(value = "/test2")
    public String test() {
        System.out.println("=========docker test222=========");
        return "this is docker test";
    }
}
