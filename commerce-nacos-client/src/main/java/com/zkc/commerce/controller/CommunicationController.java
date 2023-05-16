package com.zkc.commerce.controller;

import com.zkc.commerce.service.communication.UseRestTemplateService;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微服务通信
 */
@RestController
@RequestMapping("/communication")
@RequiredArgsConstructor
public class CommunicationController {
	
	private final UseRestTemplateService useRestTemplateService;
	
	@PostMapping("/rest-template")
	public JwtToken getTokenFromAuthService(@RequestBody UsernameAndPassword usernameAndPassword) {
		return useRestTemplateService.getTokenFromAuthService(usernameAndPassword);
	}
	
	@PostMapping("/rest-template-load-balance")
	public JwtToken getTokenFromAuthServiceWithLoadBalance(@RequestBody UsernameAndPassword usernameAndPassword) {
		return useRestTemplateService.getTokenFromAuthServiceWithLoadBalance(usernameAndPassword);
	}
}
