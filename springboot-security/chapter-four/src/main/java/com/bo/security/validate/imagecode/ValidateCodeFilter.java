package com.bo.security.validate.imagecode;

import com.bo.security.constants.StringConstants;
import com.bo.security.controller.ValidateController;
import com.bo.security.dto.ImageCode;
import com.bo.security.exception.ValidateCodeException;
import com.bo.security.security.CustomAuthenticationFailureHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @auther: bo
 * @Date: 2020/10/16 14:35
 * @version:
 * @description:
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    CustomAuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.endsWithIgnoreCase("/login", httpServletRequest.getRequestURI())
                && StringUtils.endsWithIgnoreCase(httpServletRequest.getMethod(), "post")) {
            try {
                validateCode(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
    }

    private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, StringConstants.SESSION_KEY_IMAGE_CODE);
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (Objects.isNull(codeInSession)) {
            throw new ValidateCodeException("验证码不存在！");
        }
        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidateController.SESSION_KEY_IMAGE_CODE);
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不正确！");
        }
    }
}
