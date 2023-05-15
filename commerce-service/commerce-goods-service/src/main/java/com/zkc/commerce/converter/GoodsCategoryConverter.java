package com.zkc.commerce.converter;

import com.zkc.commerce.constant.GoodsCategory;
import jakarta.persistence.AttributeConverter;

/**
 * 商品类别枚举属性转换器
 */
public class GoodsCategoryConverter implements AttributeConverter<GoodsCategory, String> {
	
	/**
	 * 转换成可以存入数据表的基本类型
	 */
	@Override
	public String convertToDatabaseColumn(GoodsCategory goodsCategory) {
		return goodsCategory.getCode();
	}
	
	/**
	 * 还原数据表中的字段值到JAVA数据类型
	 */
	@Override
	public GoodsCategory convertToEntityAttribute(String code) {
		return GoodsCategory.of(code);
	}
	
}
