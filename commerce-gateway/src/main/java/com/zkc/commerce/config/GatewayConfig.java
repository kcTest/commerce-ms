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
	@Value("${spring.cloud.nacos.discovery.server-addr}")
	public static String NACOS_SERER_ADDR;
	
	/**
	 * 命名空间
	 */
	@Value("${spring.cloud.nacos.discovery.namespace}")
	public static String NACOS_NAMESPACE;
	
	/**
	 * data-id
	 */
	@Value("${nacos.gateway.route.config.data-id}")
	public static String NACOS_ROUTE_DATA_ID;
	
	/**
	 * nacos分组id
	 */
	@Value("${nacos.gateway.route.config.group}")
	public static String NACOS_ROUTE_GROUP;
	
}
