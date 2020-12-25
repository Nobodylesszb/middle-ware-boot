package com.jwt.token.demo.exceptionHandle;

import com.jwt.token.demo.common.Payload;
import com.jwt.token.demo.constants.ResultEnum;
import com.jwt.token.demo.exceptions.ArgumentValidErrorException;
import com.jwt.token.demo.exceptions.NonLoginException;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by donh on 2018/11/6.
 * 全局异常统一处理
 */
@Slf4j
@RestControllerAdvice
public class MyControllerAdvice {
    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Payload<String> errorHandler(Exception ex) {
        log.error("系统异常：", ex);
        return new Payload<>(null, ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
    }

    /**
     * HttpRequestMethodNotSupportedException
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> methodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
        return new Payload<>(null, "400", ex.getMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> httHandler(MissingServletRequestParameterException ex) {
        return new Payload<>(null, "400", ex.getMessage());
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     *
     * @param ex
     * @return
     */
//    @ExceptionHandler(value = ApplicationException.class)
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    public Payload<String> myErrorHandler(ApplicationException ex) {
//        return new Payload<>(null, ex.getCode(), ex.getMessage());
//    }


    @ExceptionHandler(value = ArgumentValidErrorException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> argumentValidErrorExceptionHandler(ArgumentValidErrorException ex) {
        return new Payload<>(null, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = NonLoginException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Payload<String> nonLoginExceptionHandler(NonLoginException ex) {
        return new Payload<>(null, ex.getCode(), ex.getMessage());
    }

    /**
     * 拦截捕获 @RequestBody 参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> validExceptionHandler(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new Payload<>(null, "400", message);
    }

    /**
     * 拦截捕获数据绑定时异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> validExceptionHandler(BindException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new Payload<String>(null, "400", message);
    }

    /**
     * 拦截捕获 @RequestParam 参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> validExceptionHandler(ConstraintViolationException ex) {
        Iterator<ConstraintViolation<?>> it = ex.getConstraintViolations().iterator();
        String message = "";
        if (it.hasNext()) {
            message = it.next().getMessageTemplate();
        }
        return new Payload<>(null, "400", message);
    }

    /**
     * 拦截捕获 @RequestBody required=true 绑定请求参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> validExceptionHandler(HttpMessageNotReadableException ex) {
        return new Payload<String>(null, "400", "没有请求体");
    }

    /**
     * 拦截捕获绑定请求参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = UnexpectedTypeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Payload<String> validExceptionHandler(UnexpectedTypeException ex) {
        return new Payload<>(null, "400", "参数类型不对");
    }

}
