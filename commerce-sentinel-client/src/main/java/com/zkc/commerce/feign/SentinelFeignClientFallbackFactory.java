package com.zkc.commerce.feign;

import com.zkc.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * openfeign降级处理
 */
@Slf4j
@Component
public class SentinelFeignClientFallbackFactory implements FallbackFactory<SentinelFeignClient> {
	
	
	@Override
	public SentinelFeignClient create(Throwable cause) {
		return new SentinelFeignClient() {
			@Override
			public CommonResponse<String> emptyMethod(Integer code) {
				log.error("SentinelFeignClientFallbackFactory openfeign降级处理 测试请求不存在的服务接口，[{}]", code);
				return new CommonResponse<>(
						-1, "sentinel feign fallbackFactory", "code:" + code
				);
			}
		};
	}
}
