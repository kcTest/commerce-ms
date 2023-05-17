package com.zkc.commerce.config;


import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureAfter(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {
	
	/**
	 * 注入并自定义okHttp配置
	 */
	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder()
				.connectTimeout(5, TimeUnit.SECONDS)
				.readTimeout(5, TimeUnit.SECONDS)
				.writeTimeout(5, TimeUnit.SECONDS)
				.retryOnConnectionFailure(true)
				//配置连接池中最大空闲线程个数为10 并保持5分钟
				.connectionPool(new ConnectionPool(
						10, 5L, TimeUnit.MINUTES
				))
				.build();
	}
}
