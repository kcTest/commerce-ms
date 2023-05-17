package com.zkc.commerce.service.communication;

import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 与授权中心服务通信的feignClient
 */
@Slf4j
@Component
public class AuthFeignClientFallbackFactory implements FallbackFactory<AuthFeignClient> {
	
	@Override
	public AuthFeignClient create(Throwable cause) {
		return new AuthFeignClient() {
			@Override
			public JwtToken getTokenByFeign(UsernameAndPassword usernameAndPassword) {
				log.error("调用授权中心服务熔断，异常：{}", cause.getMessage());
				return null;
			}
		};
	}
}
