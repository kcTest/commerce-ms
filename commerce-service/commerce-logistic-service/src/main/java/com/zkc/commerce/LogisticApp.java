package com.zkc.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LogisticApp {
	
	public static void main(String[] args) {
		SpringApplication.run(LogisticApp.class, args);
	}
}
