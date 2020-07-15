package com.bo.elk.aop;

import com.alibaba.fastjson.JSON;
import com.bo.elk.annotation.ControllerWebLog;
import com.bo.elk.db.mapper.WebLogMapper;
import com.bo.elk.db.model.WebLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Printable;
import java.awt.print.PrinterGraphics;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口日志切面
 *
 * @author boå
 */
@Aspect
@Component
@Order(100)
@Slf4j
public class WebLogAspect {


    @Autowired
    private WebLogMapper webLogMapper;

    @Value("${spring.application.name}")
    private String serviceName;

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String START_TIME = "startTime";

    private static final String REQUEST_PARAMS = "requestParams";
    private static final String REQUEST_METHOD = "requestMethod";
    private static final String REQUEST_URL = "requestUrl";


    @Pointcut("execution(* com.bo.elk.controller..*.*(..))")
    public void webLog() {}

    @Before(value = "webLog()&& @annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLog controllerWebLog) {
        // 开始时间。
        long startTime = System.currentTimeMillis();
        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put(START_TIME, startTime);
        // 请求参数。
        //RequestContextHolder：持有上下文的Request容器,获取到当前请求的request
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest httpServletRequest = sra.getRequest();
        String requestURI = httpServletRequest.getRequestURI();
        String remoteHost = httpServletRequest.getRemoteHost();
        String method = httpServletRequest.getMethod();
        StringBuilder requestStr = new StringBuilder();
        System.out.println(serviceName);
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                requestStr.append(arg.toString());
            }
        }
        threadInfo.put(REQUEST_PARAMS, requestStr.toString());
        threadLocal.set(threadInfo);
        log.info("{}接口开始调用:requestData={}", controllerWebLog.name(), threadInfo.get(REQUEST_PARAMS));
    }

    @AfterReturning(value = "webLog()&& @annotation(controllerWebLog)", returning = "res")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object res) {
        Integer responseCode = getResponseCode();
        System.out.println(responseCode);
        Map<String, Object> threadInfo = threadLocal.get();
        long takeTime = System.currentTimeMillis() - (long) threadInfo.getOrDefault(START_TIME, System.currentTimeMillis());
        if (controllerWebLog.intoDb()) {
            insertResult(controllerWebLog.name(), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""),
                    JSON.toJSONString(res), takeTime);
        }
        threadLocal.remove();
        log.info("{}接口结束调用:耗时={}ms,result={}", controllerWebLog.name(),
                takeTime, res);
    }

    @AfterThrowing(value = "webLog()&& @annotation(controllerWebLog)", throwing = "throwable")
    public void doAfterThrowing(ControllerWebLog controllerWebLog, Throwable throwable) {
        Map<String, Object> threadInfo = threadLocal.get();
        if (controllerWebLog.intoDb()) {
            insertError(controllerWebLog.name(), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""),
                    throwable);
        }
        threadLocal.remove();
        log.error("{}接口调用异常，异常信息{}",controllerWebLog.name(), throwable);
    }


    public void insertResult(String operationName, String requestStr, String responseStr, long takeTime) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(false);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setResponse(responseStr);
        webLog.setTaketime(takeTime);
        webLogMapper.insert(webLog);
    }


    public void insertError(String operationName, String requestStr, Throwable throwable) {
        WebLog webLog = new WebLog();
        webLog.setCreateTime(new Date());
        webLog.setError(true);
        webLog.setOperationName(operationName);
        webLog.setRequest(requestStr);
        webLog.setStack(throwable.getStackTrace().toString());
        webLogMapper.insert(webLog);
    }

    private Integer getResponseCode(){
        HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        int status = httpServletResponse.getStatus();
        return status;

    }

}
