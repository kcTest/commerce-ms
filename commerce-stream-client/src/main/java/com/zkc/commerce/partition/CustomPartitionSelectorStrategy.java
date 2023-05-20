package com.zkc.commerce.partition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;
import org.springframework.stereotype.Component;

/**
 * 决定message发送到那个分区的策略
 * <p>
 * partition-selector-name: customPartitionSelectorStrategy
 */
@Slf4j
@Component
public class CustomPartitionSelectorStrategy implements PartitionSelectorStrategy {
	
	/**
	 * @param key projectName
	 */
	@Override
	public int selectPartition(Object key, int partitionCount) {
		int partition = key.toString().hashCode() % partitionCount;
		log.info("自定义分区选择策略，[{}],[{}],[{}]", key, partitionCount, partition);
		return partition;
	}
}
