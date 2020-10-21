package com.bo.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther: bo
 * @Date: 2020/10/21 22:43
 * @version:
 * @description:
 */
@SpringBootApplication
@MapperScan(value = "com.bo.shirojwt.mapper")
public class ShiroJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtApplication.class, args);
    }
}
