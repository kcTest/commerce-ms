package com.zkc.commerce.controller;

import com.zkc.commerce.stream.CustomReceiveService;
import com.zkc.commerce.stream.CustomSendService;
import com.zkc.commerce.vo.CustomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
	
	private final CustomSendService sendService;
	private final CustomReceiveService receiveService;
	
	@PostMapping("/send-auto-process")
	public void sendAuto(@RequestBody CustomMessage message) {
		sendService.sendMessageAutoProcess(message);
	}
	
	@PostMapping("/send-manual-process")
	public void sendManual(@RequestBody CustomMessage message) {
		sendService.sendMessageManualProcess(message);
	}
	
	@GetMapping("/start-auto-process")
	public void startAutoProcess() {
		receiveService.startAutoProcess();
	}
	
}
