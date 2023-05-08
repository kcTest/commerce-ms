package com.zkc.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录用户信息 payload中解析出来
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo {
	
	/**
	 * 用户id
	 */
	private Long id;
	
	/**
	 * 用户名
	 */
	private String username;
}
