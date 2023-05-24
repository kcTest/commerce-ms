package com.zkc.commerce.stream;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumeConfig {
	
	private final String HEADER_PARTITION_KEY = "partitionKey";
	
	@Bean
	public Consumer<Message<String>> autoProcess() {
		return new Consumer<Message<String>>() {
			@Override
			public void accept(Message<String> message) {
				Object partitionIndex = message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION);
				Object partitionKey = message.getHeaders().get(HEADER_PARTITION_KEY);
				log.info("autoProcess 接收来自动处理类型消息:[{}],[{}],[{}]",
						partitionIndex, partitionKey, message.getPayload());
			}
		};
	}
	
	@Bean
	public Consumer<Message<String>> manualProcess() {
		return new Consumer<Message<String>>() {
			@Override
			public void accept(Message<String> message) {
				Object partitionIndex = message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION);
				Object partitionKey = JSON.parseObject(message.getPayload(), CustomMessage.class).getProjectName();
				log.info("线程:[{}]", Thread.currentThread().getId());
				log.info("manualProcess 接收手动处理类型消息:[{}],[{}],[{}]",
						partitionIndex, partitionKey, message.getPayload());
			}
		};
	}
	
}
