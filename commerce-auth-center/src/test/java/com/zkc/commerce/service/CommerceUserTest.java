package com.zkc.commerce.service;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.zkc.commerce.dao.CommerceUserDao;
import com.zkc.commerce.entity.CommerceUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * CommerceUser 相关测试
 */
@Slf4j
@SpringBootTest
public class CommerceUserTest {
	
	@Autowired
	private CommerceUserDao commerceUserDao;
	
	@Test
	public void createUserRecord() {
		CommerceUser commerceUser = new CommerceUser();
		commerceUser.setUsername("test");
		commerceUser.setPassword(MD5.create().digestHex16("123456xxx"));
		commerceUser.setExtraInfo("{}");
		log.info("添加用户：[{}]", JSON.toJSONString(commerceUserDao.save(commerceUser)));
	}
	
	@Test
	public void findUserByUsername() {
		log.info("查询用户[test] ：[{}]", JSON.toJSONString(commerceUserDao.findByUsername("test")));
	}
}
