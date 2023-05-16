package com.zkc.commerce;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.goods.DeductGoodsInventory;
import com.zkc.commerce.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品微服务功能测试
 */
@Slf4j
@SpringBootTest
public class GoodsServiceTest {
	
	@Autowired
	private IGoodsService goodsService;
	
	@Test
	public void testGetGoodsInfoByTableId() {
		List<Long> ids = Arrays.asList(10L, 11L, 12L);
		List<TableId.Id> tableIds = ids.stream()
				.map(TableId.Id::new).collect(Collectors.toList());
		TableId tableId = new TableId(tableIds);
		
		log.info("通过TableId查询商品详细信息: [{}]", JSON.toJSONString(goodsService.getGoodsInfoByTableId(tableId)));
	}
	
	@Test
	public void testGetSimpleGoodsInfoByPage() {
		log.info("获取分页商品信息，[{}]", JSON.toJSONString(goodsService.getSimpleGoodsInfoByPage(1)));
	}
	
	@Test
	public void testGetSimpleGoodsInfoByTableId() {
		List<Long> ids = Arrays.asList(10L, 11L, 12L);
		List<TableId.Id> tableIds = ids.stream()
				.map(TableId.Id::new).collect(Collectors.toList());
		TableId tableId = new TableId(tableIds);
		
		log.info("通过TableId查询简单商品信息: [{}]", JSON.toJSONString(goodsService.getSimpleGoodsInfoByTableId(tableId)));
	}
	
	@Test
	public void testDeductGoodsInventory() {
		List<DeductGoodsInventory> deductGoodsInventories = Arrays.asList(
				new DeductGoodsInventory(10L, 50),
				new DeductGoodsInventory(11L, 50),
				new DeductGoodsInventory(12L, 50)
		);
		
		log.info("扣减多个商品库存: [{}]", goodsService.deductGoodsInventory(deductGoodsInventories));
	}
	
}
