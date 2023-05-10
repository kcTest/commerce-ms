package com.zkc.commerce.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 事件推送 Aware: 动态更新路由网关 service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {
	
	/**
	 * 写路由定义
	 */
	private final RouteDefinitionWriter routeDefinitionWriter;
	
	/**
	 * 获取路由定义
	 */
	private final RouteDefinitionLocator routeDefinitionLocator;
	
	/**
	 * 事件发布
	 */
	private ApplicationEventPublisher publisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// 完成事件推送句柄的初始化
		this.publisher = applicationEventPublisher;
	}
	
	/**
	 * 增加路由定义
	 */
	public String addRouteDefinition(RouteDefinition routeDefinition) {
		log.info("网关增加路由定义：[{}]", routeDefinition);
		
		//保存路由定义并发布
		routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
		//发布事件通知给gateway 同步新增的路由定义
		this.publisher.publishEvent(new RefreshRoutesEvent(this));
		
		return "success";
	}
	
	/**
	 * 更新路由
	 */
	public String updateList(List<RouteDefinition> newDefinitions) {
		log.info("网关更新路由：[{}]", newDefinitions);
		
		//先拿到当前gateway中存储的路由定义
		List<RouteDefinition> oldDefinitions = this.routeDefinitionLocator.getRouteDefinitions()
				.buffer().blockFirst();
		if (!CollectionUtils.isEmpty(oldDefinitions)) {
			//先清除掉旧的路由配置
			oldDefinitions.forEach(r -> {
				log.info("删除路由定义：[{}]", r);
				deleteById(r.getId());
			});
		}
		
		//把要更新的路由定义同步到gateway中
		newDefinitions.forEach(this::updateByRouteDefinition);
		
		return "success";
	}
	
	/**
	 * 通过路由id删除路由定义
	 */
	private String deleteById(String id) {
		try {
			log.info("网关删除路由定义，id:[{}]", id);
			this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
			//发布事件通知给gateway 更新路由定义
			this.publisher.publishEvent(new RefreshRoutesEvent(this));
			return "delete success";
		} catch (Exception e) {
			log.error("网关路由定义删除失败：[{}]", e.getMessage(), e);
			return "delete fail";
		}
	}
	
	/**
	 * 更新路由
	 * <p>
	 * 更新的实现策略：删除+新增=更新
	 */
	private String updateByRouteDefinition(RouteDefinition routeDefinition) {
		try {
			log.info("更新网关路由定义:[{}]", routeDefinition);
			this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
		} catch (Exception e) {
			return "update route fail, route id not found " + routeDefinition.getId();
		}
		
		try {
			this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
			this.publisher.publishEvent(new RefreshRoutesEvent(this));
			return "update route success";
			
		} catch (Exception e) {
			return "update route fail";
		}
	}
}
