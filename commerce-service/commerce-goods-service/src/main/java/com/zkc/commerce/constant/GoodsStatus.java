package com.zkc.commerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 商品状态枚举类
 */
@Getter
@AllArgsConstructor
public enum GoodsStatus {
	
	ONLINE(101, "上线"),
	OFFLINE(102, "下线"),
	STOCK_OUT(103, "缺货"),
	;
	
	/**
	 * 状态码
	 */
	private final Integer status;
	
	/**
	 * 状态描述
	 */
	private final String description;
	
	/**
	 * 根据 code 获取 GoodsStatus
	 */
	public static GoodsStatus of(Integer status) {
		Objects.requireNonNull(status);
		return Stream.of(values()).filter(good -> good.status.equals(status))
				.findAny().orElseThrow(
						() -> new IllegalArgumentException(status + " 不存在")
				);
	}
}
