package com.zkc.commerce.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 开启异步任务支持
 * 自定义异步任务线程池
 * 异步任务异常捕获
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {
	
	/**
	 * 注入自定义线程池
	 */
	@Bean
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(20);
		executor.setKeepAliveSeconds(60);
		executor.setThreadNamePrefix("ZKC-ASYNC-");
		//所有任务结束再结束线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(60);
		//如果添加失败，那么提交任务线程会自己去执行该任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		
		return executor;
	}
	
	/**
	 * 指定系统中异步任务在出现异常时使用
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncExceptionHandler();
	}
	
	/**
	 * 自定义异步任务异常处理
	 */
	private class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
		
		@Override
		public void handleUncaughtException(Throwable ex, Method method, Object... params) {
			ex.printStackTrace();
			log.error("异步任务出现异常：[{}], 方法:[{}], 参数:[{}]",
					ex.getMessage(), method.getName(), JSON.toJSONString(params));
		}
	}
}
