package com.zkc.commerce.service;

import com.zkc.commerce.order.LogisticMessage;

/**
 * 物流相关服务接口定义
 */
public interface ILogisticService {
	
	/**
	 * 物流消息处理
	 */
	void consumeLogisticMessage(String message);
}
