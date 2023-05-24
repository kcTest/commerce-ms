package com.zkc.commerce.advice;

import com.zkc.commerce.vo.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <h1>全局异常捕获处理</h1>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
	
	@ExceptionHandler(value = Exception.class)
	public CommonResponse<String> commerceExceptionHandler(HttpServletRequest req, Exception ex) {
		CommonResponse<String> response = new CommonResponse<>(-1, ex.getMessage());
		//默认logback
		log.error("commerce service has error:[{}]", ex.getMessage(), ex);
		return response;
	}
	
}
