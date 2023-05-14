package com.zkc.commerce.service;

import com.zkc.commerce.account.BalanceInfo;

/**
 * 余额相关服务接口
 */
public interface IBalanceService {
	
	/**
	 * 获取当前用户余额信息  通过解析token得到的user_id
	 */
	BalanceInfo getCurrentUserBalanceInfo();
	
	/**
	 * 扣减用户余额
	 *
	 * @param balanceInfo 扣减的余额
	 */
	BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
