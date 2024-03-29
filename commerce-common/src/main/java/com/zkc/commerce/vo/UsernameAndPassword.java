package com.zkc.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户名和密码 登录时使用
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPassword {
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
}
