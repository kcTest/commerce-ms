package com.zkc.commerce.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扣减商品库存
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "扣减商品库存对象")
public class DeductGoodsInventory {
	
	@Schema(description = "商品主键id")
	private Long goodsId;
	
	@Schema(description = "扣减个数")
	private Integer count;
}

