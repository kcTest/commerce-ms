package com.zkc.commerce.util;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.constant.CommonConstant;
import com.zkc.commerce.vo.LoginUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;

/**
 * jwt token解析工具类
 */
public class TokenParseUtil {
	
	/**
	 * 从jwt token中解析LoginUserInfo对象
	 */
	public static LoginUserInfo parseUserInfoFromToken(String token) throws Exception {
		if (token == null) {
			return null;
		}
		
		Jws<Claims> claimsJws = parseToken(token, getPublickkey());
		Claims body = claimsJws.getBody();
		// 如果token过期 返回null
		if (body.getExpiration().before(Calendar.getInstance().getTime())) {
			return null;
		}
		//返回token中填入的用户信息
		return JSON.parseObject(body.get(CommonConstant.JET_USER_INFO_KEY).toString(), LoginUserInfo.class);
	}
	
	/**
	 * 通过公钥去解析jwt token
	 */
	private static Jws<Claims> parseToken(String token, PublicKey publicKey) {
		return Jwts.parserBuilder().setSigningKey(publicKey).build()
				.parseClaimsJws(token);
	}
	
	/**
	 * 根据本地存储的公钥获取 PublicKey 对象
	 */
	private static PublicKey getPublickkey() throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
				Base64.getDecoder().decode(CommonConstant.PUBLIC_KEY));
		return KeyFactory.getInstance("RSA").generatePublic(keySpec);
	}
	
}
