package com.zkc.commerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.dao.CommerceLogisticDao;
import com.zkc.commerce.entity.CommerceLogistic;
import com.zkc.commerce.order.LogisticMessage;
import com.zkc.commerce.service.ILogisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 物流服务接口实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogisticServiceImpl implements ILogisticService {
	
	private final CommerceLogisticDao logisticDao;
	
	@Override
	public void consumeLogisticMessage(String message) {
		log.info("接收并消费物流消息：[{}]", message);
		
		LogisticMessage logisticMessage = JSON.parseObject(message, LogisticMessage.class);
		
		CommerceLogistic commerceLogistic = new CommerceLogistic();
		commerceLogistic.setUserId(logisticMessage.getUserId());
		commerceLogistic.setOrderId(logisticMessage.getOrderId());
		commerceLogistic.setAddressId(logisticMessage.getAddressId());
		commerceLogistic.setExtraInfo(logisticMessage.getExtraInfo());
		logisticDao.save(commerceLogistic);
		
		log.info("消费物流消息成功：[{}]", commerceLogistic.getId());
	}
}
