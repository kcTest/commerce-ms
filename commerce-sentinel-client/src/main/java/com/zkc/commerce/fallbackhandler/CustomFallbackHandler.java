package com.zkc.commerce.fallbackhandler;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;

/**
 * https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81
 * sentinel 降级处理
 * <p>
 * fallback 函数签名和位置要求：
 * 返回值类型必须与原函数返回值类型一致；
 * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
 * fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
 */
@Slf4j
public class CustomFallbackHandler {
	
	/**
	 * FallbackController-getTokenFromAuthCenter 回退降级
	 */
	public static JwtToken getTokenFromAuthCenterFallback(UsernameAndPassword usernameAndPassword) {
		log.error("从授权中心获取JwtToken方法回退：[{}]", JSON.toJSONString(usernameAndPassword));
		return new JwtToken("token-getTokenFromAuthCenterFallback");
	}
	
	/**
	 * FallbackController-ignoreException 回退降级
	 */
	public static JwtToken ignoreExceptionFallback(UsernameAndPassword usernameAndPassword) {
		log.error("ignoreException方法回退：[{}]", JSON.toJSONString(usernameAndPassword));
		return new JwtToken("token-ignoreException");
	}
	
}
