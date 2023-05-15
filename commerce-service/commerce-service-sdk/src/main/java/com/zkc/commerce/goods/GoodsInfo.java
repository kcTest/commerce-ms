package com.zkc.commerce.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "详细商品信息对象")
public class GoodsInfo {
	
	@Schema(description = "商品表主键id")
	private Long id;
	
	@Schema(description = "商品类别编码")
	private String goodsCategory;
	
	@Schema(description = "商品品牌分类编码")
	private String brandCategory;
	
	@Schema(description = "商品名称")
	private String goodsName;
	
	@Schema(description = "商品图片")
	private String goodsPic;
	
	@Schema(description = "商品描述信息")
	private String goodsDescription;
	
	@Schema(description = "商品状态")
	private Integer goodsStatus;
	
	@Schema(description = "商品价格 单位:分")
	private Integer price;
	
	@Schema(description = "商品属性")
	private GoodsProperty goodsProperty;
	
	@Schema(description = "总供应量")
	private Long supply;
	
	@Schema(description = "库存")
	private Long inventory;
	
	@Schema(description = "创建时间")
	private Date createTime;
	
	@Schema(description = "更新时间")
	private Date updateTime;
	
	/**
	 * 商品属性
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(description = "商品属性对象")
	public static class GoodsProperty {
		
		@Schema(description = "尺寸")
		private String size;
		
		@Schema(description = "颜色")
		private String color;
		
		@Schema(description = "材质")
		private String material;
		
		@Schema(description = "图案")
		private String pattern;
	}
	
}
