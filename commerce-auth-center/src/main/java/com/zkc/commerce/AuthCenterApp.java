package com.zkc.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 允许jpa自动审计 自动填充字段值
 */
@SpringBootApplication
@EnableJpaAuditing
public class AuthCenterApp {
	public static void main(String[] args) {
		SpringApplication.run(AuthCenterApp.class, args);
	}
}
