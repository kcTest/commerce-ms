package com.zkc.commerce.feign;

import com.zkc.commerce.common.TableId;
import com.zkc.commerce.goods.DeductGoodsInventory;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import com.zkc.commerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品服务Feign接口，不配置服务降级，有异常抛出
 */
@FeignClient(contextId = "GoodsClient", value = "commerce-goods-service", path = "commerce-goods-service")
public interface GoodsClient {
	
	/**
	 * 通过TableId查询简单商品信息
	 */
	@PostMapping("/goods/simple-goods-info")
	CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(@RequestBody TableId tableId);
	
	/**
	 * 扣减多个商品库存
	 */
	@PutMapping("/goods/deduct-goods-inventory")
	CommonResponse<Boolean> deductGoodsInventory(@RequestBody List<DeductGoodsInventory> deductGoodsInventories);
}
