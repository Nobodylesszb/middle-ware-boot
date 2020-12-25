package com.jwt.token.demo.controller;

import com.jwt.token.demo.common.Payload;
import com.jwt.token.demo.config.JwtConfig;
import com.jwt.token.demo.utils.ThreadLocalUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TokenController {
    @Resource
    private JwtConfig jwtConfig;

    /*
     * 返参格式
     * {
     *     "userName": "ID123",
     *     "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.
     *               eyJzdWIiOiJJRDEyM3B3MTIzIiwiaWF0Ijox.
     *               SqqaZfG_g2OMijyN5eG0bPmkIQaqMRFlUvny"
     * }
     */
    // 拦截器直接放行，返回Token
    @PostMapping("/login")
    public Map<String, String> login(@RequestParam("userName") String userName,
                                     @RequestParam("passWord") String passWord) {
        Map<String, String> result = new HashMap<>();
        // 省略数据源校验
        String token = jwtConfig.getToken(userName);
        if (!StringUtils.isEmpty(token)) {
            result.put("token", token);
        }
        result.put("userName", userName);
        return result;
    }

    // 需要 Token 验证的接口
    @GetMapping("/info")
    public Payload<String> info() {
        return new Payload<>(ThreadLocalUtils.getUser());
    }
}
