package com.zkc.commerce.config;

import com.zkc.commerce.filter.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport {
	
	/**
	 * 添加拦截器配置
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginUserInfoInterceptor())
				.addPathPatterns("/**")
				.order(0);
	}
	
}
