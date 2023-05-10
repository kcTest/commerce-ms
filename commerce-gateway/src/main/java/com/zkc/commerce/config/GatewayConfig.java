package com.zkc.commerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类 读取nacos相关的配置项 配置监听器
 * 实现动态配置
 */
@Configuration
public class GatewayConfig {
	
	/**
	 * 读取配置的超时时间
	 */
	public static final long DEFAULT_TIMEOUT = 30000;
	
	/**
	 * nacos服务器地址
	 */
	public static String NACOS_SERER_ADDR;
	
	/**
	 * 命名空间
	 */
	public static String NACOS_NAMESPACE;
	
	/**
	 * data-id
	 */
	public static String NACOS_ROUTE_DATA_ID;
	
	/**
	 * nacos分组id
	 */
	public static String NACOS_ROUTE_GROUP;
	
	@Value("${spring.cloud.nacos.discovery.server-addr}")
	public void setNacosServerAdder(String nacosServerAddr) {
		NACOS_SERER_ADDR = nacosServerAddr;
	}
	
	@Value("${spring.cloud.nacos.discovery.namespace}")
	public void setNacosNamespace(String nacosNamespace) {
		NACOS_NAMESPACE = nacosNamespace;
	}
	
	@Value("${nacos.gateway.route.config.data-id}")
	public void setNacosRouteDataId(String nacosRouteDataId) {
		NACOS_ROUTE_DATA_ID = nacosRouteDataId;
	}
	
	@Value("${nacos.gateway.route.config.group}")
	public void setNacosRouteGroup(String nacosRouteGroup) {
		NACOS_ROUTE_GROUP = nacosRouteGroup;
	}
	
}
