package com.zkc.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <h1>工程启动入口</h1>
 */
@EnableFeignClients
@SpringBootApplication
public class NacosClientApp {
	public static void main(String[] args) {
		SpringApplication.run(NacosClientApp.class, args);
	}
}
