package com.muti.data.source.controller;

import com.muti.data.source.entity.AdminUser;
import com.muti.data.source.entity.UserBase;
import com.muti.data.source.service.AdminUserService;
import com.muti.data.source.service.UserBaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/data")
public class DataController {

    @Resource
    private AdminUserService adminUserService ;
    @Resource
    private UserBaseService userBaseService ;

    @RequestMapping("/queryById1")
    public AdminUser queryPage (){
        return adminUserService.selectByPrimaryKey(1) ;
    }

    @RequestMapping("/queryById2")
    public UserBase queryById () {
        return userBaseService.selectByPrimaryKey(1) ;
    }

    @RequestMapping("/insert")
    public void insert (){
        UserBase userBase = new UserBase() ;
        userBase.setUserName("smile");
        userBaseService.insert(userBase);
    }

}
