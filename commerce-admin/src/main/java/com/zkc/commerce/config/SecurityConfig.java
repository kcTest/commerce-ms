package com.zkc.commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * <h1>配置安全认证 方便其它服务注册</h1>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> {
							authorize
									//静态资源 登录页  放开
									.requestMatchers("/assets/**").permitAll()
									.requestMatchers("/login").permitAll()
									.anyRequest().authenticated();
						}
				)
				.formLogin().loginPage("/login")
				.and()
				.logout().logoutUrl("/logout")
				.and()
				//开启httpBasic 其它服务注册使用
				.httpBasic()
				.and()
				.csrf()
				//基于cookie的csrf保护
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				//指定路径忽略csrf保护
				.ignoringRequestMatchers("/instances", "/actuator/**");
		return http.build();
	}
}
