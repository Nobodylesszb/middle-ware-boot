package com.bo.springboot.admin.common.aspect;

import com.bo.springboot.admin.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: bo
 * @Date: 2020/11/4 10:53
 * @version:
 * @description:
 */
@Aspect
@Configuration
@Slf4j
public class RedisAspect {
    @Value("${spring.redis.open: false}")
    private boolean open;

    @Around("execution(* com.bo.springboot.admin.common.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if(open){
            try{
                result = point.proceed();
            }catch (Exception e){
                log.error("redis error", e);
                throw new RRException("Redis服务异常");
            }
        }
        return result;
        }
}
