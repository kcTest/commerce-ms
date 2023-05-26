package com.zkc.commerce.controller;

import com.zkc.commerce.feign.SentinelFeignClient;
import com.zkc.commerce.vo.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * sentinel+openfeign熔断降级
 */
@Slf4j
@RestController
@RequestMapping("/sentinel-feign")
@RequiredArgsConstructor
public class SentinelFeignController {
	
	private final SentinelFeignClient sentinelFeignClient;
	
	/**
	 * 通过feignClient获取结果
	 */
	@GetMapping("/get-by-feign")
	public CommonResponse<String> getResultByFeign(@RequestParam Integer code) {
		log.info("进入 SentinelFeignController getResultByFeign 方法调用");
		return sentinelFeignClient.emptyMethod(code);
	}
}
