package com.zkc.commerce.controller;

import com.zkc.commerce.service.NacosClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/nacos-client")
public class NacosClientController {
	
	@Autowired
	private NacosClientService nacosClientService;
	
	/**
	 * 根据service id 获取服务所有的实例信息
	 */
	@ResponseBody
	@GetMapping("/service-instance")
	public List<ServiceInstance> logNacosClientInfo(@RequestParam(defaultValue = "commerce-nc") String serviceId) {
		log.info("coming in log nacos client info:[{}]", serviceId);
		return nacosClientService.getNacosClientInfo(serviceId);
	}
}
