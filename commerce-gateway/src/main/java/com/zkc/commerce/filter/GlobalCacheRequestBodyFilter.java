package com.zkc.commerce.filter;

import com.zkc.commerce.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 缓存请求body的全局过滤器 方便后续过滤器也可以拿到body
 * 基于spring webflux
 */
@Slf4j
@Component
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//针对登录注册请求
		boolean isLoginOrRegister = exchange.getRequest().getURI().getPath().contains(GatewayConstant.LOGIN_URI)
				|| exchange.getRequest().getURI().getPath().contains(GatewayConstant.REGISTER_URI);
		if (exchange.getRequest().getHeaders().getContentType() == null
				|| !isLoginOrRegister) {
			return chain.filter(exchange);
		}
		//.join 拿到请求中的数据--<> DataBuffer
		return DataBufferUtils.join(exchange.getRequest().getBody())
				.flatMap(dataBuffer -> {
					
					//确保数据缓冲区不被释放
					DataBufferUtils.retain(dataBuffer);
					
					//defer just都是去创建数据源 得到当前数据的副本
					Flux<DataBuffer> cacheFlux = Flux.defer(() -> {
						return Flux.just(dataBuffer.split(0));
					});
					
					//重新包装ServerHttpRequest 重写getBody方法 能够返回请求数据
					ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
						@Override
						public Flux<DataBuffer> getBody() {
							return cacheFlux;
						}
					};
					
					//将包装之后的ServerHttpRequest继续向下传递 
					return chain.filter(exchange.mutate().request(mutatedRequest).build());
				});
	}
	
	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 1;
	}
	
}
