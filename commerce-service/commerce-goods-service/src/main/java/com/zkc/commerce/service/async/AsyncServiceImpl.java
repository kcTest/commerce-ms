package com.zkc.commerce.service.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.com.google.common.base.Stopwatch;
import com.zkc.commerce.constant.GoodsConstant;
import com.zkc.commerce.dao.CommerceGoodsDao;
import com.zkc.commerce.entity.CommerceGoods;
import com.zkc.commerce.goods.GoodsInfo;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 异步服务接口实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class AsyncServiceImpl implements IAsyncService {
	
	private final CommerceGoodsDao commerceGoodsDao;
	
	private final StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 异步任务 注解指定自定义线程池
	 * 1 将商品信息导入数据表
	 * 2 更新商品缓存
	 */
	@Async("getAsyncExecutor")
	@Override
	public void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId) {
		log.info("执行异步任务, taskId:[{}]", taskId);
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		//传入数据重复标记
		boolean isIllegal = false;
		//判断重复
		Set<String> goodsInfoSet = new HashSet<>(goodsInfos.size());
		//存放要导入的商品信息
		List<GoodsInfo> filteredGoodsInfo = new ArrayList<>(goodsInfos.size());
		
		//自定义规则过滤无效商品
		for (GoodsInfo goodsInfo : goodsInfos) {
			if (goodsInfo.getPrice() <= 0 || goodsInfo.getSupply() <= 0) {
				log.info("商品信息无效,[{}]", JSON.toJSONString(goodsInfo));
				continue;
			}
			String joinInfo = String.format("%s,%s,%s",
					goodsInfo.getGoodsCategory(), goodsInfo.getBrandCategory(), goodsInfo.getGoodsName());
			if (goodsInfoSet.contains(joinInfo)) {
				//重复标记
				isIllegal = true;
			}
			//有效商品加入
			goodsInfoSet.add(joinInfo);
			filteredGoodsInfo.add(goodsInfo);
		}
		
		//存在重复或者没有要导入的数据 返回
		if (isIllegal || CollectionUtils.isEmpty(filteredGoodsInfo)) {
			stopwatch.stop();
			log.warn("未导入任何数据:[{}]", JSON.toJSONString(filteredGoodsInfo));
			log.info("商品信息检查及导入完成,[{}]ms", stopwatch.elapsed());
			return;
		}
		
		//转换为CommerceGoods列表
		List<CommerceGoods> commerceGoodsList = filteredGoodsInfo.stream()
				.map(CommerceGoods::to)
				.collect(Collectors.toList());
		
		//存放不与数据表中商品重复的商品信息 最终要导入的数据
		List<CommerceGoods> targetGoods = new ArrayList<>(commerceGoodsList.size());
		commerceGoodsList.forEach(good -> {
			CommerceGoods goodsInDB = commerceGoodsDao.findFirstByGoodsCategoryAndBrandCategoryAndGoodsName(
					good.getGoodsCategory(), good.getBrandCategory(), good.getGoodsName()
			).orElse(null);
			//不存在 可以导入
			if (goodsInDB != null) {
				targetGoods.add(good);
			}
		});
		
		//最终入库
		List<CommerceGoods> savedGoods = commerceGoodsDao.saveAll(targetGoods);
		//同步到redis
		saveNewGoodsInfoToRedis(savedGoods);
		log.info("商品信息保存到数据库并同步到Redis: [{}]", savedGoods.size());
		
		stopwatch.stop();
		log.info("检查以及导入商品信息成功：[{}]ms", stopwatch.elapsed());
	}
	
	/**
	 * 缓存简单商品信息到Redis
	 * 键值数据类型HASH (k,v)=(key,<id,SimpleGoodsInfo(json)> )
	 */
	private void saveNewGoodsInfoToRedis(List<CommerceGoods> savedGoods) {
		List<SimpleGoodsInfo> simpleGoodsInfos = savedGoods.stream()
				.map(CommerceGoods::toSimpleGoodsInfo).collect(Collectors.toList());
		
		Map<String, String> id2jsonObject = new HashMap<>(simpleGoodsInfos.size());
		simpleGoodsInfos.forEach(
				g -> id2jsonObject.put(g.getId().toString(), JSON.toJSONString(g))
		);
		
		stringRedisTemplate.opsForHash().putAll(GoodsConstant.COMMERCE_GOODS_DICT_KEY, id2jsonObject);
	}
	
}
