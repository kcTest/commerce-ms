package com.zkc.commerce.stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binding.BindingsLifecycleController;
import org.springframework.cloud.stream.endpoint.BindingsEndpoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomReceiveService {
	
	private final String BINDINGS_INPUT_NAME_MANUAL = "manual-input";
	
	private final BindingsEndpoint endpoint;
	
	public void startAutoProcess() {
		log.info("CustomSendService 启动手动处理类型消息处理, [{}], 修改前运行状态:[{}]",
				BINDINGS_INPUT_NAME_MANUAL, endpoint.queryState(BINDINGS_INPUT_NAME_MANUAL).isRunning());
		
		//Can not re-bind an anonymous binding
		endpoint.changeState(BINDINGS_INPUT_NAME_MANUAL, BindingsLifecycleController.State.STARTED);
		
		log.info("CustomSendService 启动手动处理类型消息处理, [{}],修改后运行状态:[{}]",
				BINDINGS_INPUT_NAME_MANUAL, endpoint.queryState(BINDINGS_INPUT_NAME_MANUAL).isRunning());
	}
	
}
