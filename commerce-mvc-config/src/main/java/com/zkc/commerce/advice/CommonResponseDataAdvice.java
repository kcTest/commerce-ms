package com.zkc.commerce.advice;

import com.zkc.commerce.annotation.IgnoreResponseAdvice;
import com.zkc.commerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <h1>拦截response 实现统一响应</h1>
 * 生效范围com.zkc.commerce
 */
@RestControllerAdvice(value = "com.zkc.commerce")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
	
	/**
	 * <h2>是否需要对响应进行处理</h2>
	 */
	@Override
	//	@SuppressWarnings("all") 先不加
	public boolean supports(MethodParameter returnType, Class converterType) {
		//发现自定义注解 不需要进行统一响应的处理
		if (returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
			return false;
		}
		if (returnType.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 响应给客户端之前
	 */
	@Override
	//	@SuppressWarnings("all")
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		//定义最终返回对象
		CommonResponse<Object> commonResponse = new CommonResponse<>(0, "");
		if (body == null) {
			return commonResponse;
		} else if (body instanceof CommonResponse) {
			commonResponse = (CommonResponse<Object>) body;
		} else {
			commonResponse.setData(body);
		}
		
		return commonResponse;
	}
}
