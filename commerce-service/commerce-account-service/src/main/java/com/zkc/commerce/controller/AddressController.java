package com.zkc.commerce.controller;

import com.zkc.commerce.account.AddressInfo;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.service.IAddressService;
import com.zkc.commerce.service.impl.AddressServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户地址相关服务
 */
@Slf4j
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@Tag(name = "AddressController", description = "用户地址相关服务")
public class AddressController {
	
	private final IAddressService addressService;
	
	@Operation(summary = "创建", description = "创建用户地址信息", method = "POST")
	@PostMapping("/create-address")
	public TableId createAddressInfo(@RequestBody AddressInfo addressInfo) {
		return addressService.createAddressInfo(addressInfo);
	}
	
	@Operation(summary = "当前用户", description = "获取当前登录用户地址信息", method = "GET")
	@GetMapping("/current-Address")
	public AddressInfo getCurrentAddressInfo() {
		return addressService.getCurrentAddressInfo();
	}
	
	@Operation(summary = "获取用户地址信息", description = "通过id获取用户地址信息,id是CommerceAddress表的主键", method = "GET")
	@GetMapping("/address-info")
	public AddressInfo getAddressInfoById(@RequestParam Long id) {
		return addressService.getAddressInfoById(id);
	}
	
	@Operation(summary = "获取用户地址信息", description = "通过地址表记录id查询对应用户地址信息", method = "POST")
	@PostMapping("/address-info-by-table-id")
	public AddressInfo getAddressInfoByTableId(@RequestBody TableId tableId) {
		return addressService.getAddressInfoByTableId(tableId);
	}
	
}
