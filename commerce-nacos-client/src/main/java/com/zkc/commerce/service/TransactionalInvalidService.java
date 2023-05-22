package com.zkc.commerce.service;

import com.zkc.commerce.dao.SpringBootUserRepository;
import com.zkc.commerce.entity.JpaSpringBootUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 事务失效的场景
 *
 * @Transactional注解失效场景： <p>
 * 1. 把注解标注在非public修饰的方法上
 * <p>
 * 2. propagation(传播行为)属性配置错误(不合理)
 * <p>
 * 3. rollbackFor属性设置错误
 * <p>
 * 4. 在同一个类中的方法调用，导致事务失效
 * <p>
 * 5. 自己主动去catch,代表[没有出现]异常，导致事务失效
 * <p>
 * 6. 数据库引擎本身就不支持事务(例如MyISAM)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionalInvalidService {
	
	private final SpringBootUserRepository springBootUserRepository;
	
	/**
	 * 3. rollbackFor属性设置错误
	 */
	@Transactional
	public void wrongRollbackFor01() throws Exception {
		JpaSpringBootUser user = new JpaSpringBootUser();
		user.setUsername("test-tx");
		
		springBootUserRepository.save(user);
		
		//由于某种原因抛出异常 @Transactional默认在抛出 RuntimeException 或 Error 时回滚
		throw new IOException("为检验rollbackFor属性，抛出IO异常");
	}
	
	/**
	 * 3. rollbackFor属性设置错误
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void wrongRollbackFor02() throws Exception {
		JpaSpringBootUser user = new JpaSpringBootUser();
		user.setUsername("test-tx");
		
		springBootUserRepository.save(user);
		
		//由于某种原因抛出异常 @Transactional默认在抛出 RuntimeException 或 Error 时回滚
		throw new IOException("为检验rollbackFor属性，抛出IO异常");
	}
	
	/**
	 * 4. 在同一个类中的方法调用，导致事务失效
	 */
	public void wrongInnerCall() throws Exception {
		this.wrongRollbackFor02();
	}
	
	/**
	 * 5. 自己主动去catch,代表[没有出现]异常，导致事务失效
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void wrongTryCatch() throws Exception {
		try {
			JpaSpringBootUser user = new JpaSpringBootUser();
			user.setUsername("test-tx");
			
			springBootUserRepository.save(user);
			
			//由于某种原因抛出异常 @Transactional默认在抛出 RuntimeException 或 Error 时回滚
			throw new IOException("为检验rollbackFor属性，抛出IO异常");
		} catch (Exception e) {
			log.error("发生错误：[{}]", e.getMessage(), e);
		}
	}
}
