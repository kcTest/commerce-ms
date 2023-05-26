package com.zkc.commerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.zkc.commerce.blockhandler.CustomBlockHandler;
import com.zkc.commerce.vo.CommonResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用硬编码的方式定义流量控制规则
 */
@Slf4j
@RestController
@RequestMapping("/flow-rule")
public class FlowRuleCodeController {
	
	/**
	 * 初始化流控规则
	 */
	@PostConstruct
	public void init() {
		//流控规则集合
		List<FlowRule> flowRules = new ArrayList<>();
		
		//创建流控规则
		FlowRule flowRule = new FlowRule();
		//设置流控规则QPS，限流阈值类型
		flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		//流量控制手段 直接拒绝
		flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
		//设置受保护的资源
		flowRule.setResource("hardCode");
		//设置受保护的资源的限流阈值 QPS为1
		flowRule.setCount(1);
		flowRule.setLimitApp("default");
		
		flowRules.add(flowRule);
		
		FlowRuleManager.loadRules(flowRules);
	}
	
	/**
	 * 采用硬编码限流规则
	 * <p>
	 * value：资源名称，必需项（不能为空）
	 */
	//@SentinelResource(value = "hardCode")
	//@SentinelResource(value = "hardCode", blockHandler = "handleException")
	@SentinelResource(value = "hardCode",
			blockHandlerClass = CustomBlockHandler.class, blockHandler = "customHandleException")
	@GetMapping("/hard-code")
	public CommonResponse<String> flowRuleCode() {
		log.info("请求 flowRuleCode 方法");
		return new CommonResponse<>(0, "", "flowRuleCode");
	}
	
	public CommonResponse<String> handleException(BlockException e) {
		log.error("BlockException：[{}]", JSON.toJSONString(e.getRule()));
		return new CommonResponse<>(-1, "限流异常", e.getClass().getCanonicalName());
	}
	
}
