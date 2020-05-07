package com.bo.springbootinterceptor.common.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: bo
 * @Date: 2020/5/7 11:32
 * @Version:
 * @Description:
 */
public class PageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("执行Test1Interceptor preHandle方法-->01");

        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("token不存在");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("执行Test1Interceptor postHandle方法-->02");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("执行Test1Interceptor afterCompletion方法-->03");
    }
}
