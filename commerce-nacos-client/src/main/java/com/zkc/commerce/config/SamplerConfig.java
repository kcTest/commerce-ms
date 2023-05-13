//package com.zkc.commerce.config;
//
//import brave.sampler.RateLimitingSampler;
//import brave.sampler.Sampler;
//import org.springframework.cloud.sleuth.brave.sampler.ProbabilityBasedSampler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 使用配置代码设置采样率
// */
//@Configuration
//public class SamplerConfig {
//	
//	/**
//	 * 限速采集
//	 */
//	@Bean
//	public Sampler sampler() {
//		return RateLimitingSampler.create(100);
//	}
//	
////	/**
////	 * 概率采集 默认0.1
////	 */
////	@Bean
////	public Sampler defaultSampler() {
////		return ProbabilityBasedSampler.create(1.0f);
////	}
//	
//}
