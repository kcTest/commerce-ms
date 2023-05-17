package com.zkc.commerce.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Spring Cloud OpenFeign does not provide the following beans by default for feign, but still looks up beans of these types from the application context to create the feign client:
 * <p>
 * Logger.Level
 * <p>
 * Retryer
 * <p>
 * ErrorDecoder
 * <p>
 * Request.Options
 * <p>
 * Collection<RequestInterceptor>
 * <p>
 * SetterFactory
 * <p>
 * QueryMapEncoder
 * <p>
 * Capability (MicrometerObservationCapability and CachingCapability are provided by default)
 */
@Configuration
public class FeignConfig {
	
	@Bean
	public Logger.Level loggerLevel() {
		//# Log the headers, body, and metadata for both requests and responses. NONE, No logging (DEFAULT). 
		return Logger.Level.FULL;
	}
	
	@Bean
	public Retryer retryer() {
		return new Retryer.Default(100, SECONDS.toMillis(1), 5);
	}
	
	@Bean
	public Request.Options options() {
		return new Request.Options(6, SECONDS, 6, SECONDS, true);
	}
	
}
