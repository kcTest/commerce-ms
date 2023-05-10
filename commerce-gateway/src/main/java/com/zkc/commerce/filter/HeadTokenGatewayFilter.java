package com.zkc.commerce.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义局部过滤器 实现GatewayFilter  GlobalFilter全局
 * http请求头部携带token 验证
 * 自定义+factory+配置文件包含
 */
public class HeadTokenGatewayFilter implements GatewayFilter, Ordered {
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//从http header 中寻找 key为token value为 zkc 的键值对
		String name = exchange.getRequest().getHeaders().getFirst("token");
		if ("zkc".equals(name)) {
			return chain.filter(exchange);
		}
		//标记此次请求没有权限 并结束本次请求
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}
	
	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 2;
	}
}
