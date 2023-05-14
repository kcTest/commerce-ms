package com.zkc.commerce.controller;

import com.zkc.commerce.account.BalanceInfo;
import com.zkc.commerce.service.IBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户余额服务
 */
@Tag(name = "BalanceController", description = "用户余额服务")
@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {
	
	private final IBalanceService balanceService;
	
	@Operation(summary = "当前用户", description = "获取当前用户余额信息", method = "GET")
	@GetMapping("/current-balance")
	public BalanceInfo getCurrentUserBalanceInfo() {
		return balanceService.getCurrentUserBalanceInfo();
	}
	
	@Operation(summary = "扣减", description = "扣减用户余额", method = "PUT")
	@PutMapping("/deduct")
	public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo) {
		return balanceService.deductBalance(balanceInfo);
	}
}
