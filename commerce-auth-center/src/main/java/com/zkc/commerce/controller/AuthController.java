package com.zkc.commerce.controller;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.annotation.IgnoreResponseAdvice;
import com.zkc.commerce.service.IJWTService;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外授权服务接口
 */
@Slf4j
@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthController {
	
	private final IJWTService ijwtService;
	
	/**
	 * 登录后 授权中心生成该用户token 返回信息中没有统一响应的包装 直接返回JwtToken
	 */
	@PostMapping("/token")
	@IgnoreResponseAdvice
	public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
		//授权服务器不对外开放 
		log.info("获取token的请求参数：[{}]", JSON.toJSONString(usernameAndPassword));
//		int i = 1 / 0;
		return new JwtToken(ijwtService.generateToken(usernameAndPassword.getUsername(),
				usernameAndPassword.getPassword()));
	}
	
	/**
	 * 注册后同时返回当前用户token
	 */
	@PostMapping("/register")
	@IgnoreResponseAdvice
	public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
		log.info("用户注册的请求参数：[{}]", JSON.toJSONString(usernameAndPassword));
		return new JwtToken(ijwtService.registerUserAndGenerateToken(usernameAndPassword));
	}
	
}
