package com.zkc.commerce.config;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

/**
 * https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel
 * RestTemplate限流或降级处理方法
 */
@Slf4j
public class RestTemplateExceptionUtil {
	
	/**
	 * 限流后处理方法
	 */
	public static SentinelClientHttpResponse handleException(HttpRequest request, byte[] body,
															 ClientHttpRequestExecution execution,
															 BlockException exception) {
		log.error("RestTemplate 限流处理，[{}],[{}]",
				request.getURI().getPath(), exception.getClass().getCanonicalName());
		return new SentinelClientHttpResponse(
				JSON.toJSONString(new JwtToken("请求被Sentinel限流")));
	}
	
	/**
	 * 服务降级后处理方法
	 */
	public static SentinelClientHttpResponse handleFallback(HttpRequest request, byte[] body,
															ClientHttpRequestExecution execution,
															BlockException exception) {
		log.error("RestTemplate 服务降级处理，[{}],[{}]",
				request.getURI().getPath(), exception.getClass().getCanonicalName());
		return new SentinelClientHttpResponse(
				JSON.toJSONString(new JwtToken("请求被Sentinel降级")));
	}
}
