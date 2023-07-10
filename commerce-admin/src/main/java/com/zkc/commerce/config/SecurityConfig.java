package com.zkc.commerce.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

/**
 * <h1>配置安全认证 方便其它服务注册</h1>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AdminServerProperties adminServer;
	
	@Bean
	public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(this.adminServer.path("/"));
		
		http.authorizeHttpRequests(authorize -> {
							authorize
									//静态资源 登录页  放开
									.requestMatchers(this.adminServer.path("/assets/**")).permitAll()
									.requestMatchers(this.adminServer.path("/actuator/info")).permitAll()
									.requestMatchers(this.adminServer.path("/actuator/health")).permitAll()
									.requestMatchers(this.adminServer.path("/login")).permitAll()
									.dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
									.anyRequest().authenticated();
						}
				)
				.formLogin((formLogin) -> formLogin.loginPage(this.adminServer.path("/login")).successHandler(successHandler))
				.logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout")))
				.httpBasic(Customizer.withDefaults())
				//开启httpBasic 其它服务注册使用
				.csrf()
				//基于cookie的csrf保护
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				//指定路径忽略csrf保护
				.ignoringRequestMatchers(
						new AntPathRequestMatcher(this.adminServer.path("/instances"), POST.toString()),
						new AntPathRequestMatcher(this.adminServer.path("/instances/*"), DELETE.toString()),
						new AntPathRequestMatcher(this.adminServer.path("/actuator/**"))
				);
		return http.build();
	}
}
