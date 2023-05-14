package com.zkc.commerce.dao;

import com.zkc.commerce.entity.CommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CommerceBalance Dao 接口
 */
public interface CommerceBalanceDao extends JpaRepository<CommerceBalance, Long> {
	
	/**
	 * 根据用户id查询用户余额
	 */
	CommerceBalance findByUserId(Long userId);
}
