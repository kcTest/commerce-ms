package com.zkc.commerce.constant;

/**
 * 网关常量定义
 */
public class GatewayConstant {
	
	/**
	 * 登录uri
	 */
	public static final String LOGIN_URI = "/commerce/login";
	
	/**
	 * 注册uri
	 */
	public static final String REGISTER_URI = "/commerce/register";
	
	/**
	 * 授权中心登录后获取token的uri格式化接口
	 */
	public static final String AUTH_CENTER_TOKEN_URL_FORMAT = "http://%s:/%s/auth/authority/token";
	
	/**
	 * 授权中心注册后获取token的uri格式化接口
	 */
	public static final String AUTH_CENTER_REGISTER_URL_FORMAT = "http://%s:/%s/auth/authority/register";
}
