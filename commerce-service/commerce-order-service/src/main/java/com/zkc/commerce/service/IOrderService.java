package com.zkc.commerce.service;

import com.zkc.commerce.common.TableId;
import com.zkc.commerce.order.OrderInfo;
import com.zkc.commerce.vo.PageSimpleOrderDetail;

/**
 * 订单相关服务接口定义
 */
public interface IOrderService {
	
	/**
	 * 下单（分布式事务）：创建订单、扣减库存、扣减余额、创建物流信息（stream+kafka）
	 */
	TableId createOrder(OrderInfo orderInfo);
	
	/**
	 * 获取当前用户的订单信息
	 */
	PageSimpleOrderDetail getSimpleOrderDetailByPage(int page);
}
