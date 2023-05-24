package com.zkc.commerce.controller;

import com.zkc.commerce.common.TableId;
import com.zkc.commerce.order.OrderInfo;
import com.zkc.commerce.service.IOrderService;
import com.zkc.commerce.vo.PageSimpleOrderDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 订单服务对外接口
 */
@Tag(name = "OrderController", description = "订单服务")
@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
	
	private final IOrderService orderService;
	
	@Operation(summary = "创建", description = "购买：创建订单->扣减库存->扣减余额->发送物流信息", method = "POST")
	@PostMapping("/create")
	public TableId createOrder(@RequestBody OrderInfo orderInfo) {
		return orderService.createOrder(orderInfo);
	}
	
	@Operation(summary = "订单信息", description = "获取当前用户分页订单信息", method = "GET")
	@GetMapping("/detail")
	public PageSimpleOrderDetail getSimpleOrderDetailByPage(@RequestParam(required = false, defaultValue = "1") int page) {
		return orderService.getSimpleOrderDetailByPage(page);
	}
}
