package com.zkc.commerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.account.AddressInfo;
import com.zkc.commerce.account.BalanceInfo;
import com.zkc.commerce.account.UserAddress;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.dao.CommerceOrderDao;
import com.zkc.commerce.entity.CommerceOrder;
import com.zkc.commerce.feign.AddressClientSecured;
import com.zkc.commerce.feign.BalanceClient;
import com.zkc.commerce.feign.GoodsClient;
import com.zkc.commerce.feign.GoodsClientSecured;
import com.zkc.commerce.filter.AccessContext;
import com.zkc.commerce.goods.DeductGoodsInventory;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import com.zkc.commerce.order.LogisticMessage;
import com.zkc.commerce.order.OrderInfo;
import com.zkc.commerce.service.IOrderService;
import com.zkc.commerce.stream.LogisticMessageService;
import com.zkc.commerce.vo.PageSimpleOrderDetail;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单相关服务接口实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
	
	/**
	 * 订单表 DAO接口
	 */
	private final CommerceOrderDao orderDao;
	
	/**
	 * stream消息发送
	 */
	private final LogisticMessageService logisticMessageService;
	
	/**
	 * Feign Client
	 */
	private final AddressClientSecured addressClientSecured;
	
	private final GoodsClient goodsClient;
	
	private final GoodsClientSecured goodsClientSecured;
	
	private final BalanceClient balanceClient;
	
	/**
	 * 创建订单：分布式事务 SEATA AT
	 * 1.校验请求对象
	 * 2.创建订单
	 * 3.扣减库存
	 * 4.扣减用户余额
	 * 4.发送订单物流消息
	 */
	@GlobalTransactional(rollbackFor = Exception.class)
	@Override
	public TableId createOrder(OrderInfo orderInfo) {
		Long userId = AccessContext.getLoginUserInfo().getId();
		
		//获取地址信息
		AddressInfo addressInfo = addressClientSecured.getAddressInfoByTableId(
				new TableId(Collections.singletonList(new TableId.Id(orderInfo.getUserAddressId())))
		).getData();
		
		//1.校验请求对象，商品信息不校验 扣减库存时校验
		if (CollectionUtils.isEmpty(addressInfo.getAddressItems())) {
			throw new RuntimeException("用户地址不存在:" + orderInfo.getUserAddressId());
		}
		
		//2.创建订单
		CommerceOrder order = new CommerceOrder();
		order.setUserId(userId);
		order.setAddressId(orderInfo.getUserAddressId());
		order.setOrderDetail(JSON.toJSONString(orderInfo.getOrderItems()));
		orderDao.save(order);
		log.info("创建订单成功:[{}],[{}]", userId, order.getId());
		
		//3.扣减商品库存
		List<DeductGoodsInventory> deductGoodsInventories = orderInfo.getOrderItems().stream()
				.map(OrderInfo.OrderItem::toDeductGoodsInventory).collect(Collectors.toList());
		if (!goodsClient.deductGoodsInventory(deductGoodsInventories).getData()) {
			throw new RuntimeException("扣减库存失败");
		}
		
		//4.扣减用户余额，根据商品价格数量计算需要扣减的总额
		List<TableId.Id> ids = orderInfo.getOrderItems().stream()
				.map(i -> new TableId.Id(i.getGoodsId()))
				.collect(Collectors.toList());
		List<SimpleGoodsInfo> goodsInfos = goodsClient.getSimpleGoodsInfoByTableId(new TableId(ids)).getData();
		//goodsId->price
		Map<Long, Integer> goodsInfoMap = goodsInfos.stream()
				.collect(Collectors.toMap(SimpleGoodsInfo::getId, SimpleGoodsInfo::getPrice));
		long balance = 0;
		for (OrderInfo.OrderItem orderItem : orderInfo.getOrderItems()) {
			balance += (long) orderItem.getCount() * goodsInfoMap.get(orderItem.getGoodsId());
		}
		assert balance > 0;
		//扣减
		BalanceInfo balanceInfo = balanceClient.deductBalance(new BalanceInfo(userId, balance)).getData();
		if (balanceInfo == null) {
			throw new RuntimeException("扣减用户账户余额失败");
		}
		log.info("扣减用户账户余额成功,[{}],[{}]", order.getId(), JSON.toJSONString(balanceInfo));
		
		//5.发送订单物流消息
		LogisticMessage logisticMessage = new LogisticMessage();
		logisticMessage.setUserId(userId);
		logisticMessage.setOrderId(order.getId());
		logisticMessage.setAddressId(orderInfo.getUserAddressId());
		logisticMessage.setExtraInfo("{}");
		if (!logisticMessageService.sendMessage(logisticMessage)) {
			throw new RuntimeException("发送订单物流消息失败");
		}
		log.info("发送订单物流消息成功,[{}]", JSON.toJSONString(logisticMessage));
		
		//返回订单id
		return new TableId(Collections.singletonList(new TableId.Id(order.getId())));
	}
	
	@Override
	public PageSimpleOrderDetail getSimpleOrderDetailByPage(int page) {
		if (page <= 0) {
			page = 1;
		}
		// 每页10条,根据id倒序
		PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
		Page<CommerceOrder> orderPage = orderDao.findAllByUserId(
				AccessContext.getLoginUserInfo().getId(), pageRequest);
		
		List<CommerceOrder> orders = orderPage.getContent();
		//订单列表为空直接返回空结果
		if (CollectionUtils.isEmpty(orders)) {
			return new PageSimpleOrderDetail(false, Collections.emptyList());
		}
		
		//订单详情列表
		List<PageSimpleOrderDetail.SingleOrderDetail> orderDetails = new ArrayList<>(orders.size());
		
		//提前获取订单对应商品信息 订单id->订单商品信息
		Map<Long, List<OrderInfo.OrderItem>> orderItemMap = new HashMap<>();
		//提前获取所有订单中的商品信息
		Set<Long> goodsIdSet = new HashSet<>();
		//提前获取所有订单中的地址信息
		Set<Long> addressIdSet = new HashSet<>();
		for (CommerceOrder order : orders) {
			List<OrderInfo.OrderItem> orderItems = JSON.parseArray(order.getOrderDetail(), OrderInfo.OrderItem.class);
			orderItemMap.put(order.getId(), orderItems);
			
			List<Long> goodsIds = orderItems.stream()
					.map(OrderInfo.OrderItem::getGoodsId).collect(Collectors.toList());
			goodsIdSet.addAll(goodsIds);
			
			addressIdSet.add(order.getAddressId());
		}
		assert CollectionUtils.isNotEmpty(goodsIdSet);
		assert CollectionUtils.isNotEmpty(addressIdSet);
		//商品id->简单商品信息
		Map<Long, SimpleGoodsInfo> simpleGoodsInfoMap = goodsClientSecured
				.getSimpleGoodsInfoByTableId(new TableId(
						goodsIdSet.stream().map(TableId.Id::new).collect(Collectors.toList()))
				).getData().stream().collect(Collectors.toMap(SimpleGoodsInfo::getId, Function.identity()));
		//地址id->用户单个地址信息
		Map<Long, AddressInfo.AddressItem> addressItemMap = addressClientSecured
				.getAddressInfoByTableId(new TableId(
						addressIdSet.stream().map(TableId.Id::new).collect(Collectors.toList())
				)).getData().getAddressItems().stream()
				.collect(Collectors.toMap(AddressInfo.AddressItem::getId, Function.identity()));
		
		//构造单个订单详情对象 [订单id+用户地址信息+订单商品信息]
		for (CommerceOrder order : orders) {
			//单个订单详情对象
			PageSimpleOrderDetail.SingleOrderDetail orderDetail = new PageSimpleOrderDetail.SingleOrderDetail();
			
			//用户地址信息	
			UserAddress userAddress = addressItemMap.get(order.getAddressId()).toUserAddress();
			
			//订单商品信息列表 	 
			List<OrderInfo.OrderItem> orderItems = orderItemMap.get(order.getId());
			List<PageSimpleOrderDetail.SingleOrderGoodsItem> goodsItems = new ArrayList<>(orderItems.size());
			for (OrderInfo.OrderItem orderItem : orderItems) {
				//单个订单中的单项商品信息 [简单商品信息+商品个数]
				PageSimpleOrderDetail.SingleOrderGoodsItem singleOrderGoodsItem = new PageSimpleOrderDetail.SingleOrderGoodsItem();
				singleOrderGoodsItem.setCount(orderItem.getCount());
				singleOrderGoodsItem.setSimpleGoodsInfo(simpleGoodsInfoMap.get(orderItem.getGoodsId()));
				
				goodsItems.add(singleOrderGoodsItem);
			}
			
			orderDetail.setId(order.getId());
			orderDetail.setUserAddress(userAddress);
			orderDetail.setGoodsItems(goodsItems);
			orderDetails.add(orderDetail);
		}
		
		//分页订单详情对象
		PageSimpleOrderDetail pageSimpleOrderDetail = new PageSimpleOrderDetail();
		pageSimpleOrderDetail.setHasMore(orderPage.getTotalPages() > page);
		pageSimpleOrderDetail.setOrderDetails(orderDetails);
		return pageSimpleOrderDetail;
	}
}
