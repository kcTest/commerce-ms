package com.zkc.commerce.service.async;

import com.zkc.commerce.constant.AsyncTaskStatus;
import com.zkc.commerce.vo.AsyncTaskInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 异步任务执行监控 切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AsyncTaskMonitor {
	
	/**
	 * 异步任务管理器
	 */
	private final AsyncTaskManager asyncTaskManager;
	
	/**
	 * 环绕切面 执行前后进行处理
	 */
	@Around("execution(* com.zkc.commerce.service.async.AsyncServiceImpl.*(..))")
	public Object taskHandle(ProceedingJoinPoint joinPoint) {
		//获取任务id
		String taskId = joinPoint.getArgs()[1].toString();
		
		//获取任务信息
		AsyncTaskInfo taskInfo = asyncTaskManager.getTaskInfo(taskId);
		log.info("异步任务监控正在监控异步任务: [{}]", taskId);
		//更新异步任务为运行状态
		taskInfo.setStatus(AsyncTaskStatus.RUNNING);
		asyncTaskManager.setTaskInfo(taskInfo);
		
		AsyncTaskStatus asyncTaskStatus;
		Object result;
		try {
			result = joinPoint.proceed();
			asyncTaskStatus = AsyncTaskStatus.SUCCESS;
		} catch (Throwable e) {
			//异步任务出现异常
			result = null;
			asyncTaskStatus = AsyncTaskStatus.FAILED;
			e.printStackTrace();
			log.error("异步任务监控：异步任务[{}]失败,错误信息：[{}]", taskId, e.getMessage(), e);
		}
		
		//更新异步任务的其它信息
		taskInfo.setEndTime(new Date());
		taskInfo.setStatus(asyncTaskStatus);
		taskInfo.setTotalTime(
				String.valueOf(taskInfo.getEndTime().getTime() - taskInfo.getStartTime().getTime())
		);
		asyncTaskManager.setTaskInfo(taskInfo);
		
		return result;
	}
}
