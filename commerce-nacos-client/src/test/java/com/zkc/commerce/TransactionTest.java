package com.zkc.commerce;

import com.zkc.commerce.entity.JpaSpringBootUser;
import com.zkc.commerce.dao.SpringBootUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * spring transaction 测试
 */
@SpringBootTest
public class TransactionTest {
	
	@Autowired
	private SpringBootUserRepository springBootUserRepository;
	
	/**
	 * 测试保存表记录的事务问题
	 * <p>
	 * 1. 正常只有@Test， 不会回滚新增的记录
	 * <p>
	 * 2. 加上@Transational， 会回滚掉新增的记录
	 * TRACE [commerce-nc,,] 177556 --- [           main] o.s.t.c.transaction.TransactionContext   :
	 * Rolled back transaction (1) for test context:
	 * [DefaultTestContext@71b01319 testClass = com.zkc.commerce.TransactionTest,
	 * <p>
	 * 3. 如果已经有了@Transactional注解在类上面，但是想要不会滚单个测试方法 可以指定@Rollback(value = false)
	 * TRACE [commerce-nc,,] 178984 --- [           main] o.s.t.c.transaction.TransactionContext   :
	 * Committed transaction (1) for test context:
	 * [DefaultTestContext@28fdce67 testClass = com.zkc.commerce.TransactionTest,
	 */
	@Rollback(value = false)
	@Transactional
	@Test
	public void testCreateSpringBootUser() {
		JpaSpringBootUser user = new JpaSpringBootUser();
		user.setUsername("test");
		
		springBootUserRepository.save(user);
	}
}
