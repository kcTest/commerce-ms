package com.zkc.commerce.config;

import com.alibaba.cloud.seata.web.SeataHandlerInterceptor;
import com.zkc.commerce.filter.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	
	/**
	 * 添加拦截器配置
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginUserInfoInterceptor())
				.addPathPatterns("/**")
				.order(0);
		//Seata传递XID | SeataHandlerInterceptorConfiguration 
		registry.addInterceptor(new SeataHandlerInterceptor()).addPathPatterns("/**");
		//依赖 seata-spring-boot-starter 时，自动代理数据源，无需额外处理。
	}
	
}
