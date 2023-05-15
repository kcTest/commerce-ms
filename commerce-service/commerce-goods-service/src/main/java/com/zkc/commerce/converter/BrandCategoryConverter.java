package com.zkc.commerce.converter;

import com.zkc.commerce.constant.BrandCategory;
import jakarta.persistence.AttributeConverter;

/**
 * 商品品牌分类枚举属性转换器
 */
public class BrandCategoryConverter implements AttributeConverter<BrandCategory, String> {
	
	/**
	 * 转换成可以存入数据表的基本类型
	 */
	@Override
	public String convertToDatabaseColumn(BrandCategory brandCategory) {
		return brandCategory.getCode();
	}
	
	/**
	 * 还原数据表中的字段值到JAVA数据类型
	 */
	@Override
	public BrandCategory convertToEntityAttribute(String code) {
		return BrandCategory.of(code);
	}
	
}
