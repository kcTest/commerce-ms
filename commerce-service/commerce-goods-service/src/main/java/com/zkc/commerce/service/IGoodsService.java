package com.zkc.commerce.service;

import com.zkc.commerce.common.TableId;
import com.zkc.commerce.goods.DeductGoodsInventory;
import com.zkc.commerce.goods.GoodsInfo;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import com.zkc.commerce.vo.PageSimpleGoodsInfo;

import java.util.List;

/**
 * 商品相关服务接口
 */
public interface IGoodsService {
	
	/**
	 * 通过TableId查询商品详细信息
	 */
	List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);
	
	/**
	 * 获取分页商品信息
	 */
	PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page);
	
	/**
	 * 通过TableId查询简单商品信息
	 */
	List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);
	
	/**
	 * 扣减多个商品库存
	 */
	Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);
	
}
