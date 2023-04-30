package com.zkc.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>通用响应对象的结构定义</h1>
 * {
 * "code":0,
 * "message":"",
 * "data":{}
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
	
	/**
	 * 错误码
	 */
	private Integer code;
	
	/**
	 * 错误消息
	 */
	private String message;
	
	/**
	 * 泛型响应数据
	 */
	private T data;
	
	/**
	 * 没有数据时使用
	 *
	 * @param code    错误码
	 * @param message 错误消息
	 */
	public CommonResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
