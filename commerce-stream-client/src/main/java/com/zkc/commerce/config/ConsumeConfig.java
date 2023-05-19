package com.zkc.commerce.config;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumeConfig {
	
	@Bean
	public Consumer<Message<CustomMessage>> autoProcess() {
		return new Consumer<Message<CustomMessage>>() {
			@Override
			public void accept(Message<CustomMessage> message) {
				log.info("CustomReceiveService 接收自动处理类型消息:[{}]", JSON.toJSONString(message.getPayload()));
			}
		};
	}
	
	@Bean
	public Consumer<Message<CustomMessage>> manualProcess() {
		return new Consumer<Message<CustomMessage>>() {
			@Override
			public void accept(Message<CustomMessage> message) {
				log.info("CustomReceiveService 接收手动处理类型消息:[{}]", JSON.toJSONString(message.getPayload()));
			}
		};
	}
	
}
