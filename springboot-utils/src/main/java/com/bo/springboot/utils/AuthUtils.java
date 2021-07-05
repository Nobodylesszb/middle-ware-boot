package com.bo.springboot.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/9/15.
 * <p> 用户信息获取工具类 </p>
 */
public final class AuthUtils {

    public static String currentTenant() {
        return getHttpServletRequest().getHeader("tenant");
    }

    public static String currentUser() {
        return getHttpServletRequest().getHeader("user");
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            throw new RuntimeException("failed to load request.");
        }

        return servletRequestAttributes.getRequest();
    }
}
