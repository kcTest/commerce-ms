package com.zkc.commerce.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 通过nacos下发动态路由配置 监听nacos中路由配置变更
 */
@Slf4j
@Component
@DependsOn({"gatewayConfig"})
@RequiredArgsConstructor
public class DynamicRouteServiceImplByNacos {
	
	/**
	 * Nacos配置服务
	 */
	private ConfigService configService;
	
	private final DynamicRouteServiceImpl dynamicRouteService;
	
	/**
	 * bean注入后被执行
	 */
	@PostConstruct
	public void init() {
		log.info("网关路由初始化...");
		try {
			//初始化Nacos配置服务
			configService = iniConfigService();
			if (configService == null) {
				log.error("初始化配置服务失败");
			}
			
			//通过nacos config 并指定路由配置路径去获取路由配置
			String configInfo = configService.getConfig(
					GatewayConfig.NACOS_ROUTE_DATA_ID,
					GatewayConfig.NACOS_ROUTE_GROUP,
					GatewayConfig.DEFAULT_TIMEOUT
			);
			
			log.info("获取当前网关路由配置信息:[{}]", configInfo);
			List<RouteDefinition> definitions = JSON.parseArray(configInfo, RouteDefinition.class);
			if (!CollectionUtils.isEmpty(definitions)) {
				for (RouteDefinition definition : definitions) {
					log.info("初始化网关路由配置:[{}]", definition);
					dynamicRouteService.addRouteDefinition(definition);
				}
			}
		} catch (Exception e) {
			log.error("网关路由初始化出错:[{}]", e.getMessage(), e);
		}
		
		//设置监听器
		dynamicRouteByNacosListener(
				GatewayConfig.NACOS_ROUTE_DATA_ID, GatewayConfig.NACOS_ROUTE_GROUP
		);
	}
	
	/**
	 * 初始化 ConfigService
	 */
	private ConfigService iniConfigService() {
		try {
			Properties properties = new Properties();
			properties.setProperty("serverAddr", GatewayConfig.NACOS_SERER_ADDR);
			properties.setProperty("namespace", GatewayConfig.NACOS_NAMESPACE);
			return configService = NacosFactory.createConfigService(properties);
		} catch (Exception e) {
			log.error("初始化网关nacos配置失败:[{}]", e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 监听Nacos下发的动态路由配置
	 */
	private void dynamicRouteByNacosListener(String dataId, String group) {
		try {
			//给Nacos Config 客户端增加一个监听器
			configService.addListener(dataId, group, new Listener() {
				/**
				 * 可以提供自定义线程池
				 */
				@Override
				public Executor getExecutor() {
					return null;
				}
				
				/**
				 * 接收到配置的变更信息
				 * @param configInfo Nacos中最新的配置定义
				 */
				@Override
				public void receiveConfigInfo(String configInfo) {
					log.info("开始更新配置:[{}]", configInfo);
					List<RouteDefinition> definitions = JSON.parseArray(configInfo, RouteDefinition.class);
					log.info("更新路由：[{}]", definitions.toString());
					dynamicRouteService.updateList(definitions);
				}
			});
		} catch (NacosException e) {
			log.error("动态更新网关配置出现错误:[{}]", e.getMessage(), e);
		}
	}
	
}
