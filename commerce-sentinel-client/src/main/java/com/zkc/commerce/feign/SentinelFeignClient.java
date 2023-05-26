package com.zkc.commerce.feign;

import com.zkc.commerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * sentinel支持openfeign熔断降级
 */
@FeignClient(contextId = "SentinelFeignClient", value = "empty",
//		fallback = SentinelFeignClientFallback.class,
		fallbackFactory = SentinelFeignClientFallbackFactory.class)
public interface SentinelFeignClient {
	
	@RequestMapping(value = "/empty", method = RequestMethod.GET)
	CommonResponse<String> emptyMethod(@RequestParam Integer code);
}
