package com.zkc.commerce.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * 调用时 把Header也传递到服务提供方，比如token等信息
 */
@Slf4j
@Configuration
public class FeignConfig {
	
	/**
	 * 给feign配置请求拦截器
	 * 不能传递当前请求的content-length到下游服务 因为每个请求获取的响应内容长度可能不一样，会导致响应数据被截断或无法正常返回
	 *
	 * @return 提供给open-feign的请求拦截器 传递header信息
	 */
	@Bean
	public RequestInterceptor headerInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				ServletRequestAttributes attributes =
						(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				if (attributes != null) {
					HttpServletRequest request = attributes.getRequest();
					Enumeration<String> headerNames = request.getHeaderNames();
					if (headerNames != null) {
						while (headerNames.hasMoreElements()) {
							String headName = headerNames.nextElement();
							String headValue = request.getHeader(headName);
							if (!"content-length".equals(headName)) {
								template.header(headName, headValue);
							}
						}
					}
				}
			}
		};
	}
}
