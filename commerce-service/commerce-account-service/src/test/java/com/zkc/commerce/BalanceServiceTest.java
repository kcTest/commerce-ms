package com.zkc.commerce;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.account.BalanceInfo;
import com.zkc.commerce.service.IBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户余额相关服务测试
 */
@Slf4j
public class BalanceServiceTest extends BaseTest {
	
	@Autowired
	private IBalanceService balanceService;
	
	/**
	 * 测试获取当前用户的余额信息
	 */
	@Test
	public void testGetCurrentUserBalanceInfo() {
		log.info("测试获取当前用户的余额信息:[{}]", JSON.toJSONString(
				balanceService.getCurrentUserBalanceInfo()
		));
	}
	
	/**
	 * 测试扣减用户余额
	 */
	@Test
	public void testDeductUserBalanceInfo() {
		BalanceInfo balanceInfo = new BalanceInfo();
		balanceInfo.setUserId(loginUserInfo.getId());
		balanceInfo.setBalance(10L);
		
		log.info("测试扣减用户余额：[{}]", JSON.toJSONString(
				balanceService.deductBalance(balanceInfo)
		));
	}
	
}
