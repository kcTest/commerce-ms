package com.zkc.commerce.feign;

import com.zkc.commerce.account.BalanceInfo;
import com.zkc.commerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户账户服务Feign接口，不配置服务降级，有异常抛出
 */
@FeignClient(contextId = "BalanceClient", value = "commerce-account-service", path = "/commerce-account-service")
public interface BalanceClient {
	
	@PutMapping(path = "/balance/deduct")
	CommonResponse<BalanceInfo> deductBalance(@RequestBody BalanceInfo balanceInfo);
}
