package com.zkc.commerce.dao;

import com.zkc.commerce.entity.CommerceAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CommerceAddress Dao 接口
 */
public interface CommerceAddressDao extends JpaRepository<CommerceAddress, Long> {
	
	/**
	 * 根据用户id查询地址信息
	 */
	List<CommerceAddress> findAllByUserId(Long userId);
}
