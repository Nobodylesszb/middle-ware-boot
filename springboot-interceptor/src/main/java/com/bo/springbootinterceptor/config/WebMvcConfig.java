package com.bo.springbootinterceptor.config;

import com.bo.springbootinterceptor.common.interceptor.PageInterceptor;
import com.bo.springbootinterceptor.common.interceptor.Test1Interceptor;
import com.bo.springbootinterceptor.common.interceptor.Test2Interceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Auther: bo
 * @Date: 2020/5/7 11:34
 * @Version:
 * @Description:
 */
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*
     * 拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new Test1Interceptor()) // 添加拦截器1
                .addPathPatterns("/**") // 添加拦截路径
                .excludePathPatterns(// 添加排除拦截路径
                        "/hello","/page")
                .order(0);
        registry.addInterceptor(new Test2Interceptor()) // 添加拦截器2
                .addPathPatterns("/**") // 添加拦截路径
                .excludePathPatterns(// 添加排除拦截路径
                        "/test1","/page")
                .order(1);
        registry.addInterceptor(new PageInterceptor()) // 添加拦截器page
                .addPathPatterns("/page") // 添加拦截路径
                .order(2);
        super.addInterceptors(registry);
    }
    /*
     *设置视图解析器
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry);
        registry.addViewController("/hello").setViewName("hello");
    }
}
