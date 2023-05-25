package com.zkc.commerce.stream;

import com.zkc.commerce.service.ILogisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * 接收物流消息
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConsumeConfig {
	
	private final ILogisticService logisticService;
	
	@Bean
	public Consumer<Message<String>> logisticProcess() {
		return new Consumer<Message<String>>() {
			@Override
			public void accept(Message<String> message) {
				logisticService.consumeLogisticMessage(message.getPayload());
			}
		};
	}
	
}
