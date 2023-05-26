package com.zkc.commerce.feign;

import com.zkc.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * openfeign降级处理
 */
@Slf4j
@Component
public class SentinelFeignClientFallback implements SentinelFeignClient {
	
	@Override
	public CommonResponse<String> emptyMethod(Integer code) {
		log.error("SentinelFeignClientFallback openfeign降级处理 测试请求不存在的服务接口，[{}]", code);
		return new CommonResponse<>(
				-1, "sentinel feign fallback", "code:" + code
		);
	}
}
