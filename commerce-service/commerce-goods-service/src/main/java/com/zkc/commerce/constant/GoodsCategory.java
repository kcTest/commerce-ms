package com.zkc.commerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 商品类别
 */
@Getter
@AllArgsConstructor
public enum GoodsCategory {
	
	DIAN_QI("10001", "电器"),
	JIA_JU("10002", "家具"),
	FU_SHI("10003", "服饰"),
	MU_YING("10004", "母婴"),
	SHI_PIN("10005", "食品"),
	TU_SHU("10006", "图书"),
	;
	
	/**
	 * 商品类别编码
	 */
	private final String code;
	
	/**
	 * 商品类别描述信息
	 */
	private final String description;
	
	/**
	 * 根据code获取GoodsCategory
	 */
	public static GoodsCategory of(String code) {
		Objects.requireNonNull(code);
		
		return Stream.of(values()).filter(cate -> cate.code.equals(code))
				.findAny().orElseThrow(
						() -> new IllegalArgumentException(code + " 不存在")
				);
	}
}
