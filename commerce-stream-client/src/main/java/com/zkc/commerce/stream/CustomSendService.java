package com.zkc.commerce.stream;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.CustomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomSendService {
	
	private final String BINDINGS_OUTPUT_NAME_AUTO = "auto-output";
	private final String BINDINGS_OUTPUT_NAME_MANUAL = "manual-output";
	private final StreamBridge streamBridge;
	private final String HEADER_PARTITION_KEY = "partitionKey";
	private final String HEADER_PARTITION_KEY_VALUE = "partitionKey";
	
	public void sendMessageAutoProcess(CustomMessage customMessage) {
		String message = JSON.toJSONString(customMessage);
		log.info("CustomSendService  发送自动处理类型消息:[{}]", message);
		streamBridge.send(BINDINGS_OUTPUT_NAME_AUTO, MessageBuilder.withPayload(message)
				.setHeader(HEADER_PARTITION_KEY, HEADER_PARTITION_KEY_VALUE).build());
	}
	
	public void sendMessageManualProcess(CustomMessage customMessage) {
		String message = JSON.toJSONString(customMessage);
		log.info("CustomSendService 发送手动处理类型消息:[{}]", message);
		streamBridge.send(BINDINGS_OUTPUT_NAME_MANUAL, MessageBuilder.withPayload(message).build());
	}
	
}
