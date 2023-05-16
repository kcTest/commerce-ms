package com.zkc.commerce.config;

import com.zkc.commerce.model.SwaggerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * swagger配置
 */
@Configuration
public class SpringDocConfig extends BaseSpringDocConfig {
	
	@Value("${swagger.title}")
	private String title;
	
	@Value("${swagger.description}")
	private String description;
	
	@Override
	public SwaggerProperties swaggerProperties() {
		return SwaggerProperties.builder()
				.title(title)
				.description(description)
				.version("1.0")
				.enableSecurity(true)
				.build();
	}
}