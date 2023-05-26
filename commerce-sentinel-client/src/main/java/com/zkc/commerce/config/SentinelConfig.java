package com.zkc.commerce.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SentinelConfig {
	
	/**
	 * 对 RestTemplate 的服务调用使用 Sentinel 进行保护
	 * 限流处理blockHandler
	 * 降级处理fallback
	 */
	@Bean
//	@SentinelRestTemplate(blockHandlerClass = RestTemplateExceptionUtil.class, blockHandler = "handleException",
//			fallbackClass = RestTemplateExceptionUtil.class, fallback = "handleFallback")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
