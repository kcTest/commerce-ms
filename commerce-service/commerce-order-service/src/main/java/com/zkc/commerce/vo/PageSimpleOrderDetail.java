package com.zkc.commerce.vo;

import com.zkc.commerce.account.UserAddress;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 订单详情
 */
@Schema(description = "分页订单详情对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSimpleOrderDetail {
	
	@Schema(description = "是否有更多的订单")
	private Boolean hasMore;
	
	@Schema(description = "订单详情")
	private List<SingleOrderDetail> orderDetails;
	
	@Schema(description = "单个订单详情对象")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SingleOrderDetail {
		
		@Schema(description = "订单表主键id")
		private Long id;
		
		@Schema(description = "用户地址信息")
		private UserAddress userAddress;
		
		@Schema(description = "订单商品信息列表")
		private List<SingleOrderGoodsItem> goodsItems;
	}
	
	@Schema(description = "单个订单中的单项商品信息")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SingleOrderGoodsItem {
		
		@Schema(description = "简单商品信息")
		private SimpleGoodsInfo simpleGoodsInfo;
		
		@Schema(description = "商品个数")
		private Integer count;
	}
	
	
}
