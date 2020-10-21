package com.bo.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther: bo
 * @Date: 2020/10/21 21:26
 * @version:
 * @description:
 */
@SpringBootApplication
@MapperScan(value = "com.bo.shiro.mapper")
public class ShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroApplication.class, args);
    }
}
