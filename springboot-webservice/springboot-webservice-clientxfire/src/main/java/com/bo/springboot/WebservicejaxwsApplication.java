package com.bo.springboot;

import com.bo.springboot.user.UserDto;
import com.bo.springboot.user.UserService;
import com.oracle.tools.packager.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther: bo
 * @Date: 2020/11/17 17:46
 * @version:
 * @description:
 */
@SpringBootApplication
public class WebservicejaxwsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebservicejaxwsApplication.class, args);
        UserService userService = new UserService();
        UserDto userDto = userService.getUserPortName().getUserById("1");
        int save = userService.getUserPortName().save(userDto);
        Log.info("已经保存了" + save);
        System.out.println("userdto " + save);
        System.out.println("userdto " + userDto.getUserName());
    }
}