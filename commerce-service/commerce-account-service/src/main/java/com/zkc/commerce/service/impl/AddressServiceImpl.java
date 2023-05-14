package com.zkc.commerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.account.AddressInfo;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.dao.CommerceAddressDao;
import com.zkc.commerce.entity.CommerceAddress;
import com.zkc.commerce.filter.AccessContext;
import com.zkc.commerce.service.IAddressService;
import com.zkc.commerce.vo.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户地址相关服务接口实现
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
	
	private final CommerceAddressDao commerceAddressDao;
	
	/**
	 * 存储多个地址信息
	 * 不从参数中获取user_id使用
	 */
	@Override
	public TableId createAddressInfo(AddressInfo addressInfo) {
		LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
		
		//将参数转出实体对象 暂不做校验
		List<CommerceAddress> commerceAddresses = addressInfo.getAddressItems()
				.stream().map(item -> CommerceAddress.to(loginUserInfo.getId(), item))
				.collect(Collectors.toList());
		//保存为数据表并返回id
		List<CommerceAddress> saveRecords = commerceAddressDao.saveAll(commerceAddresses);
		List<Long> ids = saveRecords.stream().map(CommerceAddress::getId).collect(Collectors.toList());
		log.info("创建地址信息:[{}],[{}]", loginUserInfo.getId(), JSON.toJSONString(ids));
		List<TableId.Id> tableIds = ids.stream().map(TableId.Id::new).collect(Collectors.toList());
		return new TableId(tableIds);
	}
	
	@Override
	public AddressInfo getCurrentAddressInfo() {
		LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
		
		//根据user_id查询地址信息 再转换
		List<CommerceAddress> commerceAddresses = commerceAddressDao.findAllByUserId(loginUserInfo.getId());
		
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setUserId(loginUserInfo.getId());
		addressInfo.setAddressItems(commerceAddresses.stream()
				.map(CommerceAddress::toAddressItem).collect(Collectors.toList()));
		return addressInfo;
	}
	
	@Override
	public AddressInfo getAddressInfoById(Long id) {
		CommerceAddress commerceAddress = commerceAddressDao.findById(id).orElse(null);
		if (null == commerceAddress) {
			throw new RuntimeException("地址不存在");
		}
		return new AddressInfo(commerceAddress.getUserId(),
				Collections.singletonList(commerceAddress.toAddressItem()));
	}
	
	@Override
	public AddressInfo getAddressInfoByTableId(TableId tableId) {
		List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
		log.info("用过TableId获取地址信息:[{}]", JSON.toJSONString(ids));
		
		List<CommerceAddress> commerceAddresses = commerceAddressDao.findAllById(ids);
		if (CollectionUtils.isEmpty(commerceAddresses)) {
			return new AddressInfo(-1L, Collections.emptyList());
		}
		
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setUserId(commerceAddresses.get(0).getUserId());
		addressInfo.setAddressItems(commerceAddresses.stream()
				.map(CommerceAddress::toAddressItem).collect(Collectors.toList()));
		return addressInfo;
	}
	
}
