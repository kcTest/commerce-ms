package com.zkc.commerce.service;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.util.TokenParseUtil;
import com.zkc.commerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * jwt相关服务测试类
 */
@Slf4j
@SpringBootTest
public class JWTServiceTest {
	
	@Autowired
	private IJWTService ijwtService;
	
	@Test
	public void testGenerateAndParseToken() throws Exception {
		String jwtToken = ijwtService.generateToken("test", "e087dae60e744ea80722b785a75adbb7");
		log.info("jwt token is :[{}]", jwtToken);
		
		LoginUserInfo userInfo = TokenParseUtil.parseUserInfoFromToken(jwtToken);
		log.info("解析token:[{}]", JSON.toJSONString(userInfo));
	}
	
}
