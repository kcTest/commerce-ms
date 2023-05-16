package com.zkc.commerce.service.communication;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.constant.CommonConstant;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 使用RestTemplate实现微服务通信
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UseRestTemplateService {
	
	private final LoadBalancerClient loadBalancerClient;
	
	/**
	 * 从授权服务中获取JwtToken
	 * 1.写死URL
	 */
	public JwtToken getTokenFromAuthService(UsernameAndPassword usernameAndPassword) {
		String requestUrl = "http://127.0.0.1:1005/auth/authority/token";
		String requestBody = JSON.toJSONString(usernameAndPassword);
		log.info("RestTemplate 请求地址和请求体:[{}],[{}]", requestUrl, requestBody);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new RestTemplate().postForObject(
				requestUrl,
				new HttpEntity<>(requestBody, headers),
				JwtToken.class
		);
	}
	
	/**
	 * 从授权服务中获取JwtToken 负载均衡
	 * 2.从注册中心获取服务实例
	 */
	public JwtToken getTokenFromAuthServiceWithLoadBalance(UsernameAndPassword usernameAndPassword) {
		ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTH_CENTER_SERVICE_ID);
		log.info("注册到Nacos的服务信息:[{}]", JSON.toJSONString(serviceInstance));
		
		String requestUrl = String.format("http://%s:%s/auth/authority/token",
				serviceInstance.getHost(), serviceInstance.getPort());
		String requestBody = JSON.toJSONString(usernameAndPassword);
		log.info("RestTemplate 请求地址和请求体:[{}],[{}]", requestUrl, requestBody);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new RestTemplate().postForObject(
				requestUrl,
				new HttpEntity<>(requestBody, headers),
				JwtToken.class
		);
	}
	
	
}
