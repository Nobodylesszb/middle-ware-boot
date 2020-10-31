package com.bo.springboot.admin.module.app.controller;

import com.bo.springboot.admin.common.utils.R;
import com.bo.springboot.admin.module.app.form.LoginForm;
import com.bo.springboot.admin.module.app.service.UserService;
import com.bo.springboot.admin.module.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther: bo
 * @Date: 2020/10/31 08:55
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public R login(@Valid @RequestBody LoginForm loginForm){
        //表单登录
        long userId = userService.login(loginForm);
        //生成token
        String token = jwtUtils.generateToken(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        return R.ok(map);
    }
}
