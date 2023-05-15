package com.zkc.commerce.service.async;

import com.zkc.commerce.constant.AsyncTaskStatus;
import com.zkc.commerce.goods.GoodsInfo;
import com.zkc.commerce.vo.AsyncTaskInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 异步任务执行管理器
 * 对异步任务进行包装管理 添加异步任务执行信息
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncTaskManager {
	
	/**
	 * 异步任务执行信息容器
	 */
	private final Map<String, AsyncTaskInfo> taskContainer = new HashMap<>(16);
	
	private final IAsyncService asyncService;
	
	/**
	 * 初始化异步任务
	 */
	public AsyncTaskInfo initTask() {
		AsyncTaskInfo taskInfo = new AsyncTaskInfo();
		taskInfo.setTaskId(UUID.randomUUID().toString());
		taskInfo.setStatus(AsyncTaskStatus.STARTED);
		taskInfo.setStartTime(new Date());
		
		taskContainer.put(taskInfo.getTaskId(), taskInfo);
		return taskInfo;
	}
	
	/**
	 * 提交异步任务
	 */
	public AsyncTaskInfo submit(List<GoodsInfo> goodsInfos) {
		//初始化异步任务状态信息
		AsyncTaskInfo taskInfo = initTask();
		asyncService.asyncImportGoods(goodsInfos, taskInfo.getTaskId());
		return taskInfo;
	}
	
	/**
	 * 设置异步任务执行状态信息
	 */
	public void setTaskInfo(AsyncTaskInfo taskInfo) {
		taskContainer.put(taskInfo.getTaskId(), taskInfo);
	}
	
	/**
	 * 获取异步任务执行状态信息
	 */
	public AsyncTaskInfo getTaskInfo(String taskId) {
		return taskContainer.get(taskId);
	}
	
}
