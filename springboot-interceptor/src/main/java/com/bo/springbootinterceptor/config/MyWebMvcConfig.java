package com.bo.springbootinterceptor.config;

import com.bo.springbootinterceptor.common.interceptor.PageInterceptor;
import com.bo.springbootinterceptor.common.interceptor.Test1Interceptor;
import com.bo.springbootinterceptor.common.interceptor.Test2Interceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Auther: bo
 * @Date: 2020/5/14 15:54
 * @Version:此方法已废弃
 * @Description:
 */
//public class MyWebMvcConfig implements WebMvcConfigurerAdapter {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册自定义拦截器，添加拦截路径和排除拦截路径
//        registry.addInterceptor(new Test1Interceptor()) // 添加拦截器1
//                .addPathPatterns("/**") // 添加拦截路径
//                .excludePathPatterns(// 添加排除拦截路径
//                        "/hello","/page")
//                .order(0);
//        registry.addInterceptor(new Test2Interceptor()) // 添加拦截器2
//                .addPathPatterns("/**") // 添加拦截路径
//                .excludePathPatterns(// 添加排除拦截路径
//                        "/test1","/page")
//                .order(1);
//        registry.addInterceptor(new PageInterceptor()) // 添加拦截器page
//                .addPathPatterns("/page") // 添加拦截路径
//                .order(2);
//        super.addInterceptors(registry);
//    }
//}
