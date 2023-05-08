package com.zkc.commerce.service;

import com.zkc.commerce.vo.UsernameAndPassword;

/**
 * jwt相关服务接口定义
 */
public interface IJWTService {
	
	/**
	 * 生成jwt token,使用默认的超时时间
	 */
	String generateToken(String username, String password) throws Exception;
	
	/**
	 * 生成指定超时时间的token 单位天
	 */
	String generateToken(String username, String password, int expire) throws Exception;
	
	/**
	 * 注册用户并生成token返回
	 */
	String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;
}
