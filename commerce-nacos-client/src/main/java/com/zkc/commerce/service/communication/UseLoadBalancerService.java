package com.zkc.commerce.service.communication;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.constant.CommonConstant;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 使用LoadBalancer实现微服务通信
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UseLoadBalancerService {
	
	private final RestTemplate restTemplate;
	
	private final DiscoveryClient discoveryClient;
	
	/**
	 * 从授权服务中获取JwtToken 3.LoadBalancer
	 */
	public JwtToken getTokenFromAuthServiceByLoadBalancer(UsernameAndPassword usernameAndPassword) {
		String requestUrl = String.format("http://%s/auth/authority/token",
				CommonConstant.AUTH_CENTER_SERVICE_ID);
		String requestBody = JSON.toJSONString(usernameAndPassword);
		log.info("RestTemplate 请求地址和请求体:[{}],[{}]", requestUrl, requestBody);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return restTemplate.postForObject(
				requestUrl,
				new HttpEntity<>(requestBody, headers),
				JwtToken.class
		);
	}
	
}
