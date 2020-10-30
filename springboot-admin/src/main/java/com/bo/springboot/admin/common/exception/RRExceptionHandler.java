/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.bo.springboot.admin.common.exception;

import com.bo.springboot.admin.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.Iterator;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestControllerAdvice
@Slf4j
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}


	/**
	 * 全局异常捕捉处理
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public R errorHandler(Exception ex) {
		log.error("系统异常：", ex);
		return R.error(400, ex.getMessage());
	}

	/**
	 * HttpRequestMethodNotSupportedException
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public  R methodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
		return R.error(400, ex.getMessage());
	}

	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public R httHandler(MissingServletRequestParameterException ex) {
		return R.error(400, ex.getMessage());
	}

	/**
	 * 拦截捕捉自定义异常 MyException.class
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = ApplicationException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public R myErrorHandler(ApplicationException ex) {
		return R.error(500, ex.getMessage());
	}



	/**
	 * 拦截捕获 @RequestBody 参数校验异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public R validExceptionHandler(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return R.error(400, message);
	}

	/**
	 * 拦截捕获数据绑定时异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = BindException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public R validExceptionHandler(BindException ex) {
		String message = ex.getBindingResult().getFieldError().getDefaultMessage();
		return R.error(400, message);
	}

	/**
	 * 拦截捕获 @RequestParam 参数校验异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public R validExceptionHandler(ConstraintViolationException ex) {
		Iterator<ConstraintViolation<?>> it = ex.getConstraintViolations().iterator();
		String message = "";
		if (it.hasNext()) {
			message = it.next().getMessageTemplate();
		}
		return R.error(400, message);
	}

	/**
	 * 拦截捕获 @RequestBody required=true 绑定请求参数异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public R validExceptionHandler(HttpMessageNotReadableException ex) {
		return R.error(400, "没有请求体");
	}

	/**
	 * 拦截捕获绑定请求参数异常
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = UnexpectedTypeException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public R validExceptionHandler(UnexpectedTypeException ex) {
		return R.error(400, "参数类型不对");
	}
}
