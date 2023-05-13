package com.zkc.commerce.filter;

import com.zkc.commerce.vo.LoginUserInfo;

/**
 * 使用ThreadLocal去单独存储每一个线程携带的 LoginUserInfo 信息
 * 及时清理保存到ThreadLocal中的用户信息 保证没有资源泄露以及重用时不会出现数据混乱
 */
public class AccessContext {
	
	private static final ThreadLocal<LoginUserInfo> LOGIN_USER_INFO = new ThreadLocal<>();
	
	public static LoginUserInfo getLoginUserInfo() {
		return LOGIN_USER_INFO.get();
	}
	
	public static void setLoginUserInfo(LoginUserInfo loginUserInfo) {
		LOGIN_USER_INFO.set(loginUserInfo);
	}
	
	public static void clearLoginUserInfo() {
		LOGIN_USER_INFO.remove();
	}
}
