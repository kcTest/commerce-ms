package com.zkc.commerce.controller;

import com.zkc.commerce.common.TableId;
import com.zkc.commerce.goods.DeductGoodsInventory;
import com.zkc.commerce.goods.GoodsInfo;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import com.zkc.commerce.service.IGoodsService;
import com.zkc.commerce.vo.PageSimpleGoodsInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品功能服务对外api
 */
@Tag(name = "GoodsController", description = "商品功能服务接口")
@Slf4j
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
	
	private final IGoodsService goodsService;
	
	@Operation(summary = "详细商品信息", description = "根据TableId查询详细商品信息", method = "POST")
	@PostMapping("/goods-info")
	public List<GoodsInfo> getGoodsInfoByTableId(@RequestBody TableId tableId) {
		return goodsService.getGoodsInfoByTableId(tableId);
	}
	
	@Operation(summary = "简单商品信息", description = "获取分页的简单商品信息", method = "GET")
	@GetMapping("/page-simple-goods-info")
	public PageSimpleGoodsInfo getGoodsInfoByTableId(@RequestParam(defaultValue = "1", required = false) int page) {
		return goodsService.getSimpleGoodsInfoByPage(page);
	}
	
	@Operation(summary = "简单商品信息", description = "通过TableId查询简单商品信息", method = "POST")
	@PostMapping("/simple-goods-info")
	public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(@RequestBody TableId tableId) {
		return goodsService.getSimpleGoodsInfoByTableId(tableId);
	}
	
	@Operation(summary = "扣减库存", description = "扣减多个商品库存", method = "PUT")
	@PutMapping("/deduct-goods-inventory")
	public Boolean deductGoodsInventory(@RequestBody List<DeductGoodsInventory> deductGoodsInventories) {
		return goodsService.deductGoodsInventory(deductGoodsInventories);
	}
	
}
