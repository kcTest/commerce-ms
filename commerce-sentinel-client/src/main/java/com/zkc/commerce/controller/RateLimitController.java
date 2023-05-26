package com.zkc.commerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zkc.commerce.blockhandler.CustomBlockHandler;
import com.zkc.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用sentinel dashboard 配置流控规则
 */
@Slf4j
@RestController
@RequestMapping("/dashboard")
public class RateLimitController {
	
	/**
	 * dashboard中的 流控规则 中根据资源名新增流控规则
	 */
	@SentinelResource(value = "byResource", blockHandlerClass = CustomBlockHandler.class,
			blockHandler = "customHandleException")
	@GetMapping("/by-resource")
	public CommonResponse<String> byResource() {
		log.info("进入 RateLimitController  byResource 方法");
		return new CommonResponse<>(0, "", "byResource");
	}
	
	/**
	 * dashboard中的 簇点链路 中根据url新增流控规则
	 */
	@SentinelResource(value = "byUrl")
	@GetMapping("/by-url")
	public CommonResponse<String> byUrl() {
		log.info("进入 RateLimitController  ByUrl 方法");
		return new CommonResponse<>(0, "", "ByUrl");
	}
	
}
