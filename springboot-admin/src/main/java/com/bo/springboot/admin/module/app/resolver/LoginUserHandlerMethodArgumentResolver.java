package com.bo.springboot.admin.module.app.resolver;

import com.bo.springboot.admin.module.app.annotation.LoginUser;
import com.bo.springboot.admin.module.app.entity.UserEntity;
import com.bo.springboot.admin.module.app.interceptor.AuthorizationInterceptor;
import com.bo.springboot.admin.module.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @auther: bo
 * @Date: 2020/11/1 22:54
 * @version:
 * @description:
 */

@Component
public class LoginUserHandlerMethodArgumentResolver  implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest request, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            return null;
        }

        //获取用户信息
        UserEntity user = userService.getById((Long)object);

        return user;
    }
}
