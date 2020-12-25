package com.jwt.token.demo.interceptor;


import com.jwt.token.demo.config.JwtConfig;
import com.jwt.token.demo.exceptions.NonLoginException;
import com.jwt.token.demo.utils.ThreadLocalUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token 拦截器
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        // 地址过滤
//        String uri = request.getRequestURI();
//        if (uri.contains("/login")) {
//            return true;
//        }
        // Token 验证
        String token = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(jwtConfig.getHeader());
        }
        if (StringUtils.isEmpty(token)) {
            throw new NonLoginException(jwtConfig.getHeader() + "失效，请重新登录");
//            handleFalseResponse(response);
        }
        Claims claims = jwtConfig.getTokenClaim(token);
        if (claims == null || jwtConfig.isTokenExpired(claims.getExpiration())) {
            throw new NonLoginException(jwtConfig.getHeader() + "失效，请重新登录");
        }
        //设置 identityId 用户身份ID
        ThreadLocalUtils.setUser(claims.getSubject());
        request.setAttribute("identityId", claims.getSubject());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.removeUser();
    }

//    private void handleFalseResponse(HttpServletResponse response)
//            throws Exception {
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(JSON.toJSONString(new Payload<>("", "403", "没有访问权限")));
//        response.getWriter().flush();
//    }
}
