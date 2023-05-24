package com.zkc.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Unable to make field protected java.lang.reflect.InvocationHandler java.lang.reflect.Proxy.h accessible:
 * module java.base does not "opens java.lang.reflect" to unnamed module @3eb7fc54
 * <p>
 * --add-opens java.base/java.lang.reflect=ALL-UNNAMED
 */
@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class OrderApp {
	public static void main(String[] args) {
		SpringApplication.run(OrderApp.class, args);
	}
}
