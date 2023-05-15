package com.zkc.commerce.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单商品信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "简单商品信息对象")
public class SimpleGoodsInfo {
	
	@Schema(description = "商品表主键id")
	private Long id;
	
	@Schema(description = "商品名称")
	private String goodsName;
	
	@Schema(description = "商品图片")
	private String goodsPic;
	
	@Schema(description = "商品价格 单位:分")
	private Integer price;
	
	public SimpleGoodsInfo(Long id) {
		this.id = id;
	}
	
}
