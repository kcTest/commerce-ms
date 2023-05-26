package com.zkc.commerce;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.zkc.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomBlockHandler {
	
	/**
	 * 通用限流处理方法 static
	 */
	public static CommonResponse<String> customHandleException(BlockException e) {
		log.error("通用限流处理方法，[{}],[{}]", JSON.toJSONString(e.getRule()), e.getRuleLimitApp());
		return new CommonResponse<>(-1, "通用限流异常", e.getClass().getCanonicalName());
	}
}
