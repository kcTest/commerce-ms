package com.zkc.commerce;

import com.zkc.commerce.filter.AccessContext;
import com.zkc.commerce.vo.LoginUserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTest {
	
	protected final LoginUserInfo loginUserInfo = new LoginUserInfo(16L, "zkc");
	
	@BeforeEach
	public void init() {
		AccessContext.setLoginUserInfo(loginUserInfo);
	}
	
	@AfterEach
	public void destroy() {
		AccessContext.clearLoginUserInfo();
	}
}
