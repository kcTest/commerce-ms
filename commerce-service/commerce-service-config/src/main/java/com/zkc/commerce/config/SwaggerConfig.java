package com.zkc.commerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * swagger配置
 */
@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openAPI() {
		
		OpenAPI openAPI = new OpenAPI().info(
				new Info()
						.title("commerce-micro-service")
						.description("commerce-springcloud-service")
						.contact(new Contact().name("zkc"))
						.version("1.0"));
		
		return openAPI;
	}
	
}
