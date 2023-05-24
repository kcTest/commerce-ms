package com.zkc.commerce.service;

import com.zkc.commerce.account.AddressInfo;
import com.zkc.commerce.common.TableId;

/**
 * 用户地址相关服务接口定义
 */
public interface IAddressService {
	
	/**
	 * 创建用户地址信息
	 */
	TableId createAddressInfo(AddressInfo addressInfo);
	
	/**
	 * 获取当前用户的地址信息 通过CommerceAddress的userid
	 */
	AddressInfo getCurrentAddressInfo();
	
	/**
	 * 通过CommerceAddress主键id获取用户地址信息
	 */
	AddressInfo getAddressInfoById(Long id);
	
	/**
	 * 通过地址表记录id查询对应用户地址信息 
	 */
	AddressInfo getAddressInfoByTableId(TableId tableId);
}
