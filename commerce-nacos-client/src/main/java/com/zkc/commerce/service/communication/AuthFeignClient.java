package com.zkc.commerce.service.communication;

import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 与授权中心服务通信的feignClient
 */
@FeignClient(contextId = "AuthFeignClient", value = "commerce-auth", path = "/auth", fallbackFactory = AuthFeignClientFallbackFactory.class)
public interface AuthFeignClient {
	
	/**
	 * 通过openfeign访问授权中心获取token
	 */
	@RequestMapping(value = "/authority/token", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	JwtToken getTokenByFeign(@RequestBody UsernameAndPassword usernameAndPassword);
}
