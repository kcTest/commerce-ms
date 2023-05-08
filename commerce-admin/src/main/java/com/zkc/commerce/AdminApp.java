package com.zkc.commerce;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h1>监控中心服务器</h1>
 */
@SpringBootApplication
@EnableAdminServer
public class AdminApp {
	public static void main(String[] args) {
		SpringApplication.run(AdminApp.class, args);
	}
}
