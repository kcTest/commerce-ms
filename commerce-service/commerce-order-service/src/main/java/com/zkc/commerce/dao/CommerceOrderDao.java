package com.zkc.commerce.dao;

import com.zkc.commerce.entity.CommerceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CommerceOrder DAO 接口
 */
public interface CommerceOrderDao extends JpaRepository<CommerceOrder, Long> {
	
	/**
	 * 根据userId查询分页订单
	 * select *
	 * from t_commerce_order
	 * where user_id = ?
	 * order by ? desc
	 * limit pageSize offset (pageNo*pageSize);
	 */
	Page<CommerceOrder> findAllByUserId(Long userId, Pageable pageable);
}
