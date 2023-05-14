package com.zkc.commerce.filter;

import com.zkc.commerce.constant.CommonConstant;
import com.zkc.commerce.util.TokenParseUtil;
import com.zkc.commerce.vo.LoginUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户身份统一登录拦截
 * 一次请求中 其它服务不需要再多次解析
 */
@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//部分请求不需要身份信息
		if (checkWhiteListUrl(request.getRequestURI())) {
			return true;
		}
		
		//从http header中拿到token
		String token = request.getHeader(CommonConstant.JET_USER_INFO_KEY);
		LoginUserInfo loginUserInfo = null;
		try {
			loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
		} catch (Exception e) {
			log.error("解析用户登录信息异常：[{}]", e.getMessage(), e);
		}
		//网关已做过解析 否则不会经过当前拦截器   
		if (loginUserInfo == null) {
			throw new RuntimeException("无法解析当前登录用户, " + request.getRequestURI());
		}
		
		//填充用户信息
		log.info("设置当前用户信息:[{}]", request.getRequestURI());
		AccessContext.setLoginUserInfo(loginUserInfo);
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//返回之前
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//完全结束 清理
		if (AccessContext.getLoginUserInfo() != null) {
			AccessContext.clearLoginUserInfo();
		}
	}
	
	private boolean checkWhiteListUrl(String url) {
		return StringUtils.containsAny(url,
				"springdoc", "swagger", "v2", "webjars", "doc.html",
				"v3", "api-docs", "error"
		);
	}
	
}