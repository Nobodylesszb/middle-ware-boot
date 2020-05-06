package com.bo.springcache.controller;

import com.bo.springcache.model.UserEntity;
import com.bo.springcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: bo
 * @Date: 2020/5/6 21:26
 * @Version:
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  查找所有
     * @return
     */
    @RequestMapping("/getAll")
    public List<UserEntity> getAll(){
        return userService.getAll();
    }
    /**
     * 根据id获取用户
     * @return
     */
    @RequestMapping("/getOne")
    public UserEntity getOne(Long id){
        return userService.getOne(id);
    }
    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping("/insertUser")
    public String insertUser(UserEntity user) {
        userService.insertUser(user);
        return "insert success";
    }
    /**
     * 修改用户
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(UserEntity user) {
        userService.updateUser(user);
        return "update success";
    }
}
