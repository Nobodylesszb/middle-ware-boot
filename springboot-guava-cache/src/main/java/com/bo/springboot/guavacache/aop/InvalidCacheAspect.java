package com.bo.springboot.guavacache.aop;

import com.bo.springboot.guavacache.annotation.InvalidCache;
import com.bo.springboot.guavacache.utils.ObjectParser;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther: bo
 * @Date: 2020/10/25 21:08
 * @version:
 * @description:
 */
@Component
@Aspect
@Slf4j
public class InvalidCacheAspect {
    @Autowired
    private Map<String, Cache> caches;


    @Pointcut("execution (* com.bo.springboot.guavacache.controller..*.*(..))")
    public void invalidCache() {
        // do nothing
    }

    @Around("invalidCache()")
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //获取注解描述
        InvalidCache invalidCache = getControllerMethodDescription(point);
        //
        if (Objects.isNull(invalidCache) || Objects.isNull(requestAttributes)) {
            return point.proceed();
        }
        //获得http请求
        HttpServletRequest request =
                ((ServletRequestAttributes) requestAttributes).getRequest();
        Long id = invalidIds(invalidCache, request, point);
        //执行该方法
        Object response = point.proceed();
        deleteCache(id, invalidCache);
        return response;
    }

    private void deleteCache(Long id, InvalidCache invalidCache) throws Exception {
        String[] names = invalidCache.name();
        for (String name : names) {
            Cache cache = caches.get(name);
            if (Objects.isNull(cache)) {
                throw new Exception("未找到缓存");
            }
            cache.invalidate(id);
            log.info("清除缓存" + id);
        }
    }

    private InvalidCache getControllerMethodDescription(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        InvalidCache invalidCache = null;
        if (method != null) {
            invalidCache = method.getAnnotation(InvalidCache.class);
        }
        return invalidCache;
    }


    private Long invalidIds(InvalidCache invalidCache, HttpServletRequest request, ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] parameterValues = point.getArgs();
        Map<String, Object> parameters = new LinkedHashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            parameters.put(parameterNames[i], parameterValues[i]);
        }
        String incomingObjectName = invalidCache.IncomingObjectName();
//        try {
//            Object o = JSONObject.parseObject(request.getInputStream(), invalidCache.IncomingObject());
//            return (List<Long>) ObjectParser.getFieldValueByName(invalidCache.invalid(), o);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String idName = invalidCache.invalid();
        Object o = parameters.get(incomingObjectName);
        return (Long) ObjectParser.getFieldValueByName(idName, o);
    }
}
