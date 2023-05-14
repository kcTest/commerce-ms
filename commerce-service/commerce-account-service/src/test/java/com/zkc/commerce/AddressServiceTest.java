package com.zkc.commerce;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.account.AddressInfo;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户地址相关服务接口测试
 */
@Slf4j
public class AddressServiceTest extends BaseTest {
	
	@Autowired
	private IAddressService addressService;
	
	/**
	 * 测试创建用户地址
	 */
	@Test
	public void testCreateAddressInfo() {
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setUserId(loginUserInfo.getId());
		
		List<AddressInfo.AddressItem> addressItems = new ArrayList<>();
		
		AddressInfo.AddressItem addressItemA = new AddressInfo.AddressItem();
		addressItemA.setUsername("zkc");
		addressItemA.setPhone("123456");
		addressItemA.setProvince("abc");
		addressItemA.setCity("xxx");
		addressItemA.setAddressDetail("{}");
		
		AddressInfo.AddressItem addressItemB = new AddressInfo.AddressItem();
		addressItemB.setUsername("zkc");
		addressItemB.setPhone("654321");
		addressItemB.setProvince("def");
		addressItemB.setCity("zzz");
		addressItemB.setAddressDetail("{}");
		
		addressItems.add(addressItemA);
		addressItems.add(addressItemB);
		
		addressInfo.setAddressItems(addressItems);
		
		log.info("测试创建用户地址:[{}]", JSON.toJSONString(addressInfo));
		TableId tableId = addressService.createAddressInfo(addressInfo);
		log.info("地址id:[{}]", tableId);
	}
	
	/**
	 * 测试获取当前用户地址信息
	 */
	@Test
	public void testGetCurrentAddressInfo() {
		log.info("获取当前用户地址信息：[{}]", JSON.toJSONString(addressService.getCurrentAddressInfo()));
	}
	
	
	/**
	 * 测试根据地址表记录id获取用户对应地址信息
	 */
	@Test
	public void testGetAddressInfo() {
		log.info("根据地址表记录id获取用户对应地址信息：[{}],[{}]", 12L,
				JSON.toJSONString(addressService.getAddressInfo(12L)));
	}
	
	/**
	 * 通过一个的用户多个地址表id获取对应地址信息
	 */
	@Test
	public void testGetAddressInfoByTableId() {
		TableId tableId = new TableId();
		tableId.setIds(Arrays.asList(new TableId.Id(12L), new TableId.Id(13L)));
		log.info("通过用户多个地址表id获取对应地址信息：[{}],[{}]", JSON.toJSONString(tableId.getIds()),
				JSON.toJSONString(addressService.getAddressInfoByTableId(tableId)));
	}
}
