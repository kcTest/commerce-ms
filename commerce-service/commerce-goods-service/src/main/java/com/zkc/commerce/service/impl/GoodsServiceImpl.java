package com.zkc.commerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.common.TableId;
import com.zkc.commerce.constant.GoodsConstant;
import com.zkc.commerce.dao.CommerceGoodsDao;
import com.zkc.commerce.entity.CommerceGoods;
import com.zkc.commerce.goods.DeductGoodsInventory;
import com.zkc.commerce.goods.GoodsInfo;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import com.zkc.commerce.service.IGoodsService;
import com.zkc.commerce.vo.PageSimpleGoodsInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品服务相关接口实现
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class GoodsServiceImpl implements IGoodsService {
	
	private final StringRedisTemplate stringRedisTemplate;
	
	private final CommerceGoodsDao commerceGoodsDao;
	
	@Override
	public List<GoodsInfo> getGoodsInfoByTableId(TableId tableId) {
		List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
		log.info("通过TableId查询商品信息,[{}]", JSON.toJSONString(ids));
		List<CommerceGoods> commerceGoods = commerceGoodsDao.findAllById(ids);
		
		return commerceGoods.stream().map(CommerceGoods::toGoodsInfo).collect(Collectors.toList());
	}
	
	@Override
	public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page) {
		//默认第一页
		if (page <= 1) {
			page = 1;
		}
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").descending());
		Page<CommerceGoods> goodsPage = commerceGoodsDao.findAll(pageable);
		
		PageSimpleGoodsInfo pageSimpleGoodsInfo = new PageSimpleGoodsInfo();
		pageSimpleGoodsInfo.setHasMore(goodsPage.getTotalPages() > page);
		List<SimpleGoodsInfo> simpleGoodsInfos = goodsPage.getContent().stream()
				.map(CommerceGoods::toSimpleGoodsInfo).collect(Collectors.toList());
		pageSimpleGoodsInfo.setSimpleGoodsInfos(simpleGoodsInfos);
		return pageSimpleGoodsInfo;
	}
	
	@Override
	public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId) {
		List<SimpleGoodsInfo> result = new ArrayList<>();
		
		List<Object> ids = tableId.getIds().stream().map(i -> i.getId().toString()).collect(Collectors.toList());
		if (ids.size() == 0) {
			return result;
		}
		
		//先从redis中取 
		List<Object> cachedSimpleGoodsInfo = stringRedisTemplate.opsForHash()
				.multiGet(GoodsConstant.COMMERCE_GOODS_DICT_KEY, ids)
				.stream().filter(Objects::nonNull).collect(Collectors.toList());
		
		//为空需要缓存要查询的所有商品
		if (CollectionUtils.isEmpty(cachedSimpleGoodsInfo)) {
			log.info("需要缓存当前查询的所有商品：[{}]", JSON.toJSONString(ids));
			result = queryGoodsInfoFromDbAndCacheToRedis(tableId);
		} else {
			//已缓存的商品加入返回结果中
			List<SimpleGoodsInfo> existSimpleGoodsInfos = parseCachedGoodsInfo(cachedSimpleGoodsInfo);
			//部分商品没有缓存在redis 需要缓存
			if (existSimpleGoodsInfos.size() != ids.size()) {
				
				//过滤出需要缓存的商品数据ID 
				Collection<Long> idsToCache = CollectionUtils.subtract(
						ids.stream().map(i -> Long.parseLong(i.toString())).collect(Collectors.toList()),
						existSimpleGoodsInfos.stream().map(SimpleGoodsInfo::getId).collect(Collectors.toList()));
				log.info("部分商品需从数据库查询并缓存：[{}]", JSON.toJSONString(idsToCache));
				
				//从DB查询并缓存到Redis
				List<TableId.Id> tableIds = idsToCache.stream()
						.map(TableId.Id::new).collect(Collectors.toList());
				List<SimpleGoodsInfo> cachedSimpleGoodsInfos = queryGoodsInfoFromDbAndCacheToRedis(new TableId(tableIds));
				
				//合并
				result = new ArrayList<>(CollectionUtils.union(existSimpleGoodsInfos, cachedSimpleGoodsInfos));
			} else {
				log.info("所有商品均从缓存中查询到：[{}]", JSON.toJSONString(ids));
				result = existSimpleGoodsInfos;
			}
		}
		
		return result;
	}
	
	@Override
	public Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories) {
		deductGoodsInventories.forEach(g -> {
			if (g.getCount() <= 0) {
				throw new RuntimeException("需要扣减的库存数量需要大于0");
			}
		});
		
		List<CommerceGoods> commerceGoodsList = commerceGoodsDao.findAllById(deductGoodsInventories.stream()
				.map(DeductGoodsInventory::getGoodsId).collect(Collectors.toList()));
		
		//查询不到商品抛出异常
		if (CollectionUtils.isEmpty(commerceGoodsList)) {
			throw new RuntimeException("未查到要扣减库存的商品");
		}
		//数量异常
		if (commerceGoodsList.size() != deductGoodsInventories.size()) {
			throw new RuntimeException("商品数量异常");
		}
		
		//商品对应扣减数量
		Map<Long, Integer> goodsId2Inventory = deductGoodsInventories.stream()
				.collect(Collectors.toMap(DeductGoodsInventory::getGoodsId, DeductGoodsInventory::getCount));
		
		//检查是否可以扣减
		commerceGoodsList.forEach(g -> {
			Long inventory = g.getInventory();
			Integer needDeductInventory = goodsId2Inventory.get(g.getId());
			if (inventory < needDeductInventory) {
				log.error("商品库存不足:[{}],[{}]", inventory, deductGoodsInventories);
				throw new RuntimeException("商品库存不足:[{}]" + g.getId());
			}
			g.setInventory(inventory - needDeductInventory);
			log.info("商品库存:[{}],[{}],[{}]", inventory, needDeductInventory, g.getInventory());
		});
		
		commerceGoodsDao.saveAll(commerceGoodsList);
		log.info("商品库存扣减完成");
		
		return true;
	}
	
	/**
	 * 将缓存中的Object转换成SimpleGoodsInfo
	 */
	private List<SimpleGoodsInfo> parseCachedGoodsInfo(List<Object> cachedSimpleGoodsInfo) {
		return cachedSimpleGoodsInfo.stream()
				.map(g -> JSON.parseObject(g.toString(), SimpleGoodsInfo.class)).collect(Collectors.toList());
	}
	
	/**
	 * 从数据表中查询数据缓存到Redis中
	 */
	private List<SimpleGoodsInfo> queryGoodsInfoFromDbAndCacheToRedis(TableId tableId) {
		List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
		log.info("通过TableId从DB查询商品信息,[{}]", JSON.toJSONString(ids));
		
		List<CommerceGoods> commerceGoodsList = commerceGoodsDao.findAllById(ids);
		List<SimpleGoodsInfo> simpleGoodsInfos = commerceGoodsList.stream()
				.map(CommerceGoods::toSimpleGoodsInfo).collect(Collectors.toList());
		
		//将简单商品信息缓存
		log.info("缓存商品信息：[{}]", JSON.toJSONString(simpleGoodsInfos));
		Map<String, String> id2JsonObject = new HashMap<>(simpleGoodsInfos.size());
		simpleGoodsInfos.forEach(g ->
				id2JsonObject.put(g.getId().toString(), JSON.toJSONString(g))
		);
		stringRedisTemplate.opsForHash().putAll(GoodsConstant.COMMERCE_GOODS_DICT_KEY, id2JsonObject);
		
		return simpleGoodsInfos;
	}
	
}
