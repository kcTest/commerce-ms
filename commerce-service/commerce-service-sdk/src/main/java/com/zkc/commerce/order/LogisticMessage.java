package com.zkc.commerce.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建订单时发送的物流消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "物流消息对象")
public class LogisticMessage {
	
	@Schema(description = "用户表主键id")
	private Long userId;
	
	@Schema(description = "订单表主键id")
	private Long orderId;
	
	@Schema(description = "用户地址表主键id")
	private Long addressId;
	
	@Schema(description = "备注信息 json存储")
	private String extraInfo;
}
