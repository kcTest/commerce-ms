package com.zkc.commerce.order;

import com.zkc.commerce.goods.DeductGoodsInventory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 订单信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户发起购买订单")
public class OrderInfo {
	
	@Schema(description = "用户地址表主键 id")
	private Long userAddressId;
	
	@Schema(description = "订单中的商品信息")
	private List<OrderItem> orderItems;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(description = "订单中的单项商品信息")
	public static class OrderItem {
		
		@Schema(description = "商品id")
		private Long goodsId;
		
		@Schema(description = "购买商品个数")
		private Integer count;
		
		public DeductGoodsInventory toDeductGoodsInventory() {
			return new DeductGoodsInventory(this.goodsId, count);
		}
	}
	
}
