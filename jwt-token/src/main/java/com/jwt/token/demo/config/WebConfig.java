package com.jwt.token.demo.config;

import com.jwt.token.demo.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> EXCLUDE_URLS = Arrays.asList("/webjars/**", "/swagger-resources/**",
            "/favicon.icon", "/swagger-ui.html", "/user/login");

    /**
     * 拦截器注册
     */
    @Autowired
    private TokenInterceptor tokenInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).excludePathPatterns(EXCLUDE_URLS).addPathPatterns("/**");
    }
}
