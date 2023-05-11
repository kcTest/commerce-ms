package com.zkc.commerce.controller;

import com.zkc.commerce.service.BraveTraceInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印跟踪信息
 */
@Slf4j
@RestController
@RequestMapping("/brave")
@RequiredArgsConstructor
public class BraveTraceInfoController {
	
	private final BraveTraceInfoService traceInfoService;
	
	@GetMapping("/trace-info")
	public void logCurrentTraceInfo() {
		traceInfoService.logCurrentTraceInfo();
	}
}
