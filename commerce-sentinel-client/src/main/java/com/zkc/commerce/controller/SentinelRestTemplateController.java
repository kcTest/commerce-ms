package com.zkc.commerce.controller;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 使用sentinel保护RestTemplate服务间调用
 */
@Slf4j
@RestController
@RequestMapping("/sentinel-rest-template")
@RequiredArgsConstructor
public class SentinelRestTemplateController {
	
	private final RestTemplate restTemplate;
	
	/**
	 * 从授权服务获取JwtToken
	 * 针对requestUrl进行流控(dashboard 簇点链路 中设置流控规则)及
	 * 服务降级(auth服务不可用不生效，dashboard 簇点链路 中设置熔断规则，不使用全局异常处理， auth服务中接口抛出异常)
	 */
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
}
