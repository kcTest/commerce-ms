package com.zkc.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权中鉴权之后给客户端的token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
	
	/**
	 * jwt字符串
	 */
	private String token;
	
}
