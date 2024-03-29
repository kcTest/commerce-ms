package com.zkc.commerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 商品品牌分类枚举
 */
@Getter
@AllArgsConstructor
public enum BrandCategory {
	
	BRAND_A("20001", "品牌 A"),
	BRAND_B("20002", "品牌 B"),
	BRAND_C("20003", "品牌 C"),
	BRAND_D("20004", "品牌 D"),
	BRAND_E("20005", "品牌 E"),
	;
	
	/**
	 * 品牌分类编码
	 */
	private final String code;
	
	/**
	 * 品牌分类描述信息
	 */
	private final String description;
	
	/**
	 * 根据code获取BrandCategory
	 */
	public static BrandCategory of(String code) {
		Objects.requireNonNull(code);
		
		return Stream.of(values()).filter(cate -> cate.code.equals(code))
				.findAny().orElseThrow(
						() -> new IllegalArgumentException(code + " 不存在")
				);
	}
	
}
