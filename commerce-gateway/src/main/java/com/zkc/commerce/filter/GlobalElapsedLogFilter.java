package com.zkc.commerce.filter;

import com.alibaba.nacos.shaded.com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class GlobalElapsedLogFilter implements GlobalFilter, Ordered {
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		Stopwatch sw = Stopwatch.createStarted();
		String uri = exchange.getRequest().getURI().getPath();
		
		return chain.filter(exchange).then(
				//后置
				Mono.fromRunnable(() -> {
					log.info("[{}] elapsed: [{}ms]", uri, sw.elapsed(TimeUnit.MILLISECONDS));
				})
		);
	}
	
	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 100;
	}
}
