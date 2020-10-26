package com.bo.springboot.guavacache.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 处理 HttpServletRequest
 *
 * @author bo
 * @version 1.0
 * @date 2020-09-24 17:29
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (log.isDebugEnabled()) {
            log.debug("自定义过滤器MyFilter...");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}