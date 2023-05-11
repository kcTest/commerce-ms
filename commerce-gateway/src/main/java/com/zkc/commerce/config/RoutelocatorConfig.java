package com.zkc.commerce.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {
 * "timestamp": "2023-05-11T06:11:44.335+00:00",
 * "path": "/gw/commerce/login",
 * "status": 404,
 * "error": "Not Found",
 * "message": null,
 * "requestId": "e3b6d1c6-2"
 * }
 * 代码定义配置 请求转发规则
 */
@Configuration
public class RoutelocatorConfig {
	
	/**
	 * 使用代码定义路由规则  匹配的请求由网关转发  请求会通过网关过滤器  在网关层面拦截登录和注册
	 */
	@Bean
	public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {
		//手动定义gateway路由规则 需要指定id、path和uri
		return builder.routes()
				.route("id-commerce-auth",
						r -> r.path("/gw/commerce/login", "/gw/commerce/register")
								.uri("http://localhost:1007")
				).build();
	}
	
}
