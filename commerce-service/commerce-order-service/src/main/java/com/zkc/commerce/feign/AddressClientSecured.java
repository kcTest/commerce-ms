package com.zkc.commerce.feign;

import com.zkc.commerce.account.AddressInfo;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.feign.sentinel.AddressClientSecuredFallbackFactory;
import com.zkc.commerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户账户服务Feign接口定义,配置服务降级
 */
@FeignClient(contextId = "AddressClientSecured", value = "commerce-account-service",
		path = "/commerce-account-service", fallbackFactory = AddressClientSecuredFallbackFactory.class)
public interface AddressClientSecured {
	
	/**
	 * 通过地址表记录id查询对应用户地址信息
	 */
	@PostMapping("/address/address-info-by-table-id")
	CommonResponse<AddressInfo> getAddressInfoByTableId(@RequestBody TableId tableId);
}
