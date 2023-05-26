package com.zkc.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SentinelClientApp {
	
	public static void main(String[] args) {
		SpringApplication.run(SentinelClientApp.class, args);
	}
}
