package com.zkc.commerce.config;

import com.zkc.commerce.model.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

/**
 * swagger基础配置
 */
public abstract class BaseSpringDocConfig {
	
	@Bean
	public OpenAPI openApi() {
		
		SwaggerProperties properties = swaggerProperties();
		
		OpenAPI openApi = new OpenAPI()
				.info(new Info()
						.title(properties.getTitle())
						.version(properties.getVersion())
						.description(properties.getDescription()));
		if (properties.isEnableSecurity()) {
			openApi.components(new Components()
							.addSecuritySchemes("k1",
									new SecurityScheme()
											.type(SecurityScheme.Type.APIKEY)
											.in(SecurityScheme.In.HEADER)
											.name("Authorization")))
					.addSecurityItem(new SecurityRequirement().addList("k1"));
		}
		
		return openApi;
	}
	
	public abstract SwaggerProperties swaggerProperties();
}
