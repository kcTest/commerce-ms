package com.zkc.commerce.controller;

import com.zkc.commerce.goods.GoodsInfo;
import com.zkc.commerce.service.async.AsyncTaskManager;
import com.zkc.commerce.vo.AsyncTaskInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 异步任务服务对外API
 */
@Tag(name = "AsyncGoodsController", description = "商品异步入库服务")
@RestController
@RequestMapping("/async-goods")
@RequiredArgsConstructor
public class AsyncGoodsController {
	
	private final AsyncTaskManager asyncTaskManager;
	
	@Operation(summary = "导入商品", description = "导入商品到商品表", method = "POST")
	@PostMapping("/import-goods")
	public AsyncTaskInfo importGoods(@RequestBody List<GoodsInfo> goodsInfos) {
		return asyncTaskManager.submit(goodsInfos);
	}
	
	@Operation(summary = "查询状态", description = "查询异步任务执行状态", method = "GET")
	@GetMapping("/task-info")
	public AsyncTaskInfo getTaskInfo(@RequestParam String taskId) {
		return asyncTaskManager.getTaskInfo(taskId);
	}
	
}
