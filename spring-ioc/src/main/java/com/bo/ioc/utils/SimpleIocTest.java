package com.bo.ioc.utils;

import com.bo.ioc.entity.Car;
import com.bo.ioc.entity.Wheel;

import java.util.Objects;

/**
 * @auther: bo
 * @Date: 2020/9/30 11:18
 * @version:
 * @description:
 */
public class SimpleIocTest {

    public static void main(String[] args) throws Exception {
//        System.out.println(SimpleIOC.class.getClassLoader().getResource(""));
//        System.out.println(SimpleIOC.class.getClassLoader().getResource("/"));
//        System.out.println(SimpleIOC.class.getResource("/"));
//        System.out.println(SimpleIOC.class.getResource(""));
        String location = Objects.requireNonNull(SimpleIOC.class.getClassLoader().getResource("beanConfig.xml")).getFile();
        SimpleIOC bf = new SimpleIOC(location);
        Wheel wheel = (Wheel) bf.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) bf.getBean("car");
        System.out.println(car);
    }

}
