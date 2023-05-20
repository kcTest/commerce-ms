package com.zkc.commerce.partition;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 自定义从Message中提取partition key的策略
 * <p>
 * partition-key-extractor-name: customPartitionKeyExtractorName2
 */
@Slf4j
@Component
public class CustomPartitionKeyExtractorName implements PartitionKeyExtractorStrategy {
	
	@Override
	public Object extractKey(Message<?> message) {
		CustomMessage customMessage;
		Object payload = message.getPayload();
		if (payload instanceof String) {
			customMessage = JSON.parseObject(payload.toString(), CustomMessage.class);
		} else if (payload instanceof byte[]) {
			customMessage = JSON.parseObject(new String((byte[]) payload), CustomMessage.class);
		} else {
			throw new RuntimeException("自定义分区键异常");
		}
		String key = customMessage.getProjectName();
		log.info("自定义分区键:[{}]", key);
		return key;
	}
	
}
