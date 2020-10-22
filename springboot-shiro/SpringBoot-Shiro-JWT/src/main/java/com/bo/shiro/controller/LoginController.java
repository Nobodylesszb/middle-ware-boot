package com.bo.shiro.controller;

import com.bo.shiro.mapper.UserMapper;
import com.bo.shiro.model.ResultMap;
import com.bo.shiro.shiro.JWTToken;
import com.bo.shiro.utis.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {
    private final UserMapper userMapper;
    private final ResultMap resultMap;

    @Autowired
    public LoginController(UserMapper userMapper, ResultMap resultMap) {
        this.userMapper = userMapper;
        this.resultMap = resultMap;
    }

    @PostMapping("/login")
    public ResultMap login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        String realPassword = userMapper.getPassword(username);
        if (realPassword == null) {
            return resultMap.fail().code(401).message("用户名错误");
        } else if (!realPassword.equals(password)) {
            return resultMap.fail().code(401).message("密码错误");
        } else {
            return resultMap.success().code(200).message(JWTUtil.createToken(username));
        }
    }

    @PostMapping("/login2")
    public ResultMap login2(@RequestParam("username") String username,
                            @RequestParam("password") String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return resultMap.fail().code(401).message("用户名错误");
        }
        try {
            //未校验密码
            String token = JWTUtil.createToken(username);
            Subject subject = SecurityUtils.getSubject();
            JWTToken jwtToken = new JWTToken(token);
            subject.login(jwtToken);
            return resultMap.success().code(200).message(jwtToken);
        } catch (AuthenticationException e) {
            return resultMap.fail().code(401).message("登录失败");
        }
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public ResultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return resultMap.success().code(401).message(message);
    }
}
