package com.bo.springboot.controller;

import com.bo.springboot.entity.SysUser;
import com.bo.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService userService;

    /**
     * 查找所有
     *
     * @return
     */
    @RequestMapping("/getAll")
    public List<SysUser> getAll() {
        return userService.getAll();
    }

    /**
     * 根据id获取用户
     *
     * @return
     */
    @RequestMapping("/getOne")
    public SysUser getOne(Long id) {
        return userService.getOne(id);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/insertUser")
    public String insertUser(SysUser user) {
        userService.insertUser(user);
        return "insert success";
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(SysUser user) {
        userService.updateUser(user);
        return "update success";
    }
}
