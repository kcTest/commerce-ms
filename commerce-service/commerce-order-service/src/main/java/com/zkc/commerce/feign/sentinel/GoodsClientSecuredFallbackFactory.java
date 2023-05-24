package com.zkc.commerce.feign.sentinel;

import com.zkc.commerce.common.TableId;
import com.zkc.commerce.feign.GoodsClientSecured;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import com.zkc.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 商品服务熔断降级策略
 */
@Slf4j
@Component
public class GoodsClientSecuredFallbackFactory implements FallbackFactory<GoodsClientSecured> {
	
	@Override
	public GoodsClientSecured create(Throwable cause) {
		return new GoodsClientSecured() {
			@Override
			public CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(TableId tableId) {
				log.error("远程调用 商品服务 发生熔断，[{}]", cause.getMessage(), cause);
				return new CommonResponse<>(-1, cause.getMessage(), Collections.emptyList());
			}
		};
	}
	
}
