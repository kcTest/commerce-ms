package com.zkc.commerce.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单个用户地址展示信息
 */
@Schema(description = "单个用户地址展示信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
	
	@Schema(description = "用户姓名")
	private String username;
	
	@Schema(description = "电话")
	private String phone;
	
	@Schema(description = "省")
	private String province;
	
	@Schema(description = "市")
	private String city;
	
	@Schema(description = "详细地址")
	private String addressDetail;
	
}
