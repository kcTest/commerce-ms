package com.zkc.commerce.vo;

import com.zkc.commerce.constant.AsyncTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 标记异步任务执行信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsyncTaskInfo {
	
	/**
	 * 异步任务ID
	 */
	private String taskId;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 总耗时
	 */
	private String totalTime;
	
	/**
	 * 异步任务执行状态
	 */
	private AsyncTaskStatus status;
}
