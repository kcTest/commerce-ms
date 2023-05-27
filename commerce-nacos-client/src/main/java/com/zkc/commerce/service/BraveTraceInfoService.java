package com.zkc.commerce.service;

import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 使用代码查看OpenZipkin Brave生成的跟踪信息
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BraveTraceInfoService {
	
	private final Tracer tracer;
	
	/**
	 * 打印当前的跟踪信息到日志中
	 */
	public void logCurrentTraceInfo() {
		log.info("brave trace id: [{}]", tracer.currentSpan().context().traceId());
		log.info("brave span id:[{}]", tracer.currentSpan().context().spanId());
	}
}
