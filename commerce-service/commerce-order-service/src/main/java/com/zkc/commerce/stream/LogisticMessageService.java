package com.zkc.commerce.stream;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.order.LogisticMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogisticMessageService {
	
	private final String BINDINGS_OUTPUT_NAME_AUTO = "logistic-output";
	private final StreamBridge streamBridge;
	
	public boolean sendMessage(LogisticMessage logisticMessage) {
		String message = JSON.toJSONString(logisticMessage);
		log.info("CustomSendService  发送自动处理类型消息:[{}]", message);
		return streamBridge.send(BINDINGS_OUTPUT_NAME_AUTO, MessageBuilder.withPayload(message).build());
	}
	
}
