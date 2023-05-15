package com.zkc.commerce.dao;

import com.zkc.commerce.constant.BrandCategory;
import com.zkc.commerce.constant.GoodsCategory;
import com.zkc.commerce.entity.CommerceGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * CommerceGoods DAO
 * 分页 排序
 */
public interface CommerceGoodsDao extends JpaRepository<CommerceGoods, Long> {
	
	/**
	 * 查询商品表 限制返回一条
	 */
	Optional<CommerceGoods> findFirstByGoodsCategoryAndBrandCategoryAndGoodsName(
			GoodsCategory goodsCategory, BrandCategory brandCategory, String goodsName
	);
	
}
