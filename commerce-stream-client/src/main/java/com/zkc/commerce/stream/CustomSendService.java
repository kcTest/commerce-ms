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
	
	public void sendMessageAutoProcess(CustomMessage customMessage) {
		log.info("CustomSendService  发送自动处理类型消息:[{}]", JSON.toJSONString(customMessage));
		streamBridge.send(BINDINGS_OUTPUT_NAME_AUTO, MessageBuilder.withPayload(customMessage).build());
	}
	
	public void sendMessageManualProcess(CustomMessage customMessage) {
		log.info("CustomSendService 发送手动处理类型消息:[{}]", JSON.toJSONString(customMessage));
		streamBridge.send(BINDINGS_OUTPUT_NAME_MANUAL, MessageBuilder.withPayload(customMessage).build());
	}
	
}
