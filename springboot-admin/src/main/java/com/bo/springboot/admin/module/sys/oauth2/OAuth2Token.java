package com.bo.springboot.admin.module.sys.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @auther: bo
 * @Date: 2020/11/3 09:27
 * @version:
 * @description:
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
