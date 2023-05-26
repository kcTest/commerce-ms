package com.zkc.commerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.zkc.commerce.fallbackhandler.CustomFallbackHandler;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * sentinel对资源进行服务降级
 */
@Slf4j
@RestController
@RequestMapping("/sentinel-fallback")
@RequiredArgsConstructor
public class FallbackController {
	
	/**
	 * 没有使用sentinel保护的RestTemplate
	 */
	private final RestTemplate restTemplate;
	
	/**
	 * 从授权服务获取JwtToken
	 * 针对当前资源进行服务降级
	 */
	@SentinelResource(value = "getToken", fallbackClass = CustomFallbackHandler.class,
			fallback = "getTokenFromAuthCenterFallback")
	@PostMapping("/get-token")
	public JwtToken getTokenFromAuthCenter(@RequestBody UsernameAndPassword usernameAndPassword) {
		String requestUrl = "http://127.0.0.1:1005/auth/authority/token";
		log.info("RestTemplate 请求地址和请求体：[{}],[{}]",
				requestUrl, JSON.toJSONString(usernameAndPassword));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return restTemplate.postForObject(requestUrl,
				new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
				JwtToken.class);
	}
	
	/**
	 * 针对资源进行流控 让sentinel忽略指定异常
	 */
	@GetMapping("/ignore-exception")
	@SentinelResource(value = "ignoreException", fallbackClass = CustomFallbackHandler.class,
			fallback = "ignoreExceptionFallback", exceptionsToIgnore = NullPointerException.class)
	public JwtToken ignoreException(@RequestParam Integer code) {
		if (code % 2 == 0) {
			throw new NullPointerException("code===" + code);
		}
		return new JwtToken("token-ignoreException");
	}
}
