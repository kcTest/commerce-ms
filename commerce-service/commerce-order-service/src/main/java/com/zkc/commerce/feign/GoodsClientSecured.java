package com.zkc.commerce.feign;

import com.zkc.commerce.common.TableId;
import com.zkc.commerce.feign.sentinel.GoodsClientSecuredFallbackFactory;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import com.zkc.commerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品服务Feign接口，配置服务降级
 */
@FeignClient(contextId = "SecuredGoodsClient", value = "commerce-goods-service",
		path = "commerce-goods-service", fallbackFactory = GoodsClientSecuredFallbackFactory.class)
public interface GoodsClientSecured {
	
	/**
	 * 通过TableId查询简单商品信息
	 */
	@PostMapping("/goods/simple-goods-info")
	CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(@RequestBody TableId tableId);
	
}
