package com.zkc.commerce.feign.sentinel;

import com.zkc.commerce.account.AddressInfo;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.feign.AddressClientSecured;
import com.zkc.commerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 账户服务熔断降级策略
 */
@Slf4j
@Component
public class AddressClientSecuredFallbackFactory implements FallbackFactory<AddressClientSecured> {
	@Override
	public AddressClientSecured create(Throwable cause) {
		return new AddressClientSecured() {
			@Override
			public CommonResponse<AddressInfo> getAddressInfoByTableId(TableId tableId) {
				log.error("远程调用 用户账户服务 发生熔断。[{}]", cause.getMessage(), cause);
				return new CommonResponse<>(-1, cause.getMessage(),
						new AddressInfo(-1L, Collections.emptyList()));
			}
		};
	}
}
