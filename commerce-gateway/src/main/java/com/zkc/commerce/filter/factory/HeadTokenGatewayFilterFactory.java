package com.zkc.commerce.filter.factory;

import com.zkc.commerce.filter.HeadTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class HeadTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
	
	@Override
	public GatewayFilter apply(Object config) {
		return new HeadTokenGatewayFilter();
	}
}
