package com.zkc.commerce.service.impl;

import com.zkc.commerce.account.BalanceInfo;
import com.zkc.commerce.dao.CommerceBalanceDao;
import com.zkc.commerce.entity.CommerceBalance;
import com.zkc.commerce.filter.AccessContext;
import com.zkc.commerce.service.IBalanceService;
import com.zkc.commerce.vo.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户账户余额相关服务接口实现
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BalanceServiceImpl implements IBalanceService {
	
	private final CommerceBalanceDao commerceBalanceDao;
	
	@Override
	public BalanceInfo getCurrentUserBalanceInfo() {
		LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
		
		BalanceInfo balanceInfo = new BalanceInfo();
		balanceInfo.setUserId(loginUserInfo.getId());
		balanceInfo.setBalance(0L);
		
		CommerceBalance commerceBalance = commerceBalanceDao.findByUserId(loginUserInfo.getId());
		if (commerceBalance != null) {
			balanceInfo.setBalance(commerceBalance.getBalance());
		} else {
			//创建
			CommerceBalance newBalance = new CommerceBalance();
			newBalance.setUserId(loginUserInfo.getId());
			newBalance.setBalance(0L);
			commerceBalance = commerceBalanceDao.save(newBalance);
			log.info("初始化用户余额信息：[{}]", commerceBalance);
		}
		
		return balanceInfo;
	}
	
	@Override
	public BalanceInfo deductBalance(BalanceInfo balanceInfo) {
		LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
		
		//扣减<=当前用户余额
		CommerceBalance commerceBalance = commerceBalanceDao.findByUserId(loginUserInfo.getId());
		if (commerceBalance == null || commerceBalance.getBalance() - balanceInfo.getBalance() < 0) {
			throw new RuntimeException("用户账户余额不足");
		}
		
		Long oriBalance = commerceBalance.getBalance();
		commerceBalance.setBalance(oriBalance - balanceInfo.getBalance());
		log.info("扣减余额：[{}],[{}],[{}]", commerceBalanceDao.save(commerceBalance).getId(), oriBalance, balanceInfo.getBalance());
		
		BalanceInfo newBalanceInfo = new BalanceInfo();
		newBalanceInfo.setUserId(commerceBalance.getUserId());
		newBalanceInfo.setBalance(commerceBalance.getBalance());
		return newBalanceInfo;
	}
	
}
