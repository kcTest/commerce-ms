package com.zkc.commerce.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * gateway+sentinel 限流
 * <p>
 * https://github.com/alibaba/Sentinel/wiki/%E7%BD%91%E5%85%B3%E9%99%90%E6%B5%81
 * <p>
 * 使用默认DefaultBlockRequestHandler ??
 * java.lang.NoSuchMethodError: 'org.springframework.web.reactive.function.server
 * .ServerResponse$BodyBuilder org.springframework.web.reactive.function.server.ServerResponse.
 * status(org.springframework.http.HttpStatus)'
 */
@Slf4j
@Configuration
public class SentinelGatewayConfig {
	
	/**
	 * 视图解析器
	 */
	private final List<ViewResolver> viewResolvers;
	
	/**
	 * HTTP请求和响应数据的编解码配置
	 */
	private final ServerCodecConfigurer serverCodecConfigurer;
	
	
	public SentinelGatewayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
								 ServerCodecConfigurer serverCodecConfigurer) {
		this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
		this.serverCodecConfigurer = serverCodecConfigurer;
	}
	
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
		// Register the block exception handler for Spring Cloud Gateway.
		return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
	}
	
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public GlobalFilter sentinelGatewayFilter() {
		return new SentinelGatewayFilter();
	}
	
	/**
	 * 初始化限流规则
	 */
	@PostConstruct
	public void initRules() {
		log.info("============================");
		log.info("加载sentinel网关限流规则");
		initGatewayRules();
		log.info("加载自定义sentinel网关限流处理器");
		initBlockHandlers();
		log.info("============================");
	}
	
	/**
	 * 硬编码网关限流规则
	 * commerce-nc整个服务 60s内最多允许访问三次
	 */
	private void initGatewayRules() {
		Set<GatewayFlowRule> rules = new HashSet<>();
		
		GatewayFlowRule flowRule = new GatewayFlowRule();
		//resourceMode：规则是针对 API Gateway 的 route（RESOURCE_MODE_ROUTE_ID）
		// 还是用户在 Sentinel 中定义的 API 分组（RESOURCE_MODE_CUSTOM_API_NAME），默认是 route。
		flowRule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
		//resource：资源名称，可以是网关中的 route 名称或者用户自定义的 API 分组名称。
		flowRule.setResource("commerce-nc");
		//grade：限流指标维度，同限流规则的 grade 字段。
		flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		//intervalSec：统计时间窗口，单位是秒，默认是 1 秒。
		flowRule.setIntervalSec(60);
		//count：限流阈值
		flowRule.setCount(3);
		rules.add(flowRule);
		
		GatewayRuleManager.loadRules(rules);
	}
	
	/**
	 * 自定义限流异常处理器
	 * <p>
	 * 在 GatewayCallbackManager 注册回调进行定制：
	 * <p>
	 * setBlockHandler：注册函数用于实现自定义的逻辑处理被限流的请求，对应接口为 BlockRequestHandler。
	 * 默认实现为 DefaultBlockRequestHandler，当被限流时会返回类似于下面的错误信息：Blocked by Sentinel: FlowException。
	 */
	private void initBlockHandlers() {
		BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
			@Override
			public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
				log.error("===============触发sentinel网关限流规则");
				
				Map<String, String> result = new HashMap<>();
				result.put("code", String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()));
				result.put("message", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
				result.put("route", "commerce-nc");
				
				return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
						.contentType(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromValue(result));
			}
		};
		
		GatewayCallbackManager.setBlockHandler(blockRequestHandler);
	}
}
