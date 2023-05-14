package com.zkc.commerce.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账户余额信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户账户余额信息")
public class BalanceInfo {
	
	@Schema(description = "用户id")
	private Long userId;
	
	@Schema(description = "用户账户余额或余额扣减值")
	private Long balance;
}
