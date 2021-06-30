package com.bo.security.config;

import com.bo.security.domain.MyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // 使用@Bean 注解表明myBean需要交给Spring进行管理
    // 未指定bean 的名称，默认采用的是 "方法名" + "首字母小写"的配置方式
    @Bean(value = "myBean1")
    public MyBean myBean() {
        return new MyBean();
    }

    @Bean(value = "myBean2")
    public MyBean myBean2() {
        return new MyBean();
    }
}