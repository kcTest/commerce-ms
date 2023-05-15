package com.zkc.commerce.vo;

import com.zkc.commerce.goods.SimpleGoodsInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页商品信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页商品信息对象")
public class PageSimpleGoodsInfo {
	
	@Schema(description = "分页简单商品信息")
	private List<SimpleGoodsInfo> simpleGoodsInfos;
	
	@Schema(description = "是否有更多的商品")
	private Boolean hasMore;
}
