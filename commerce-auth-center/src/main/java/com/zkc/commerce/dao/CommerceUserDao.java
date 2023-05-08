package com.zkc.commerce.dao;

import com.zkc.commerce.entity.CommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CommerceUser Dao 接口定义
 */
public interface CommerceUserDao extends JpaRepository<CommerceUser, Long> {
	
	/**
	 * 根据用户名查询CommerceUser对象
	 * <p>
	 * select * from t_commerce_user where username= ?
	 */
	CommerceUser findByUsername(String username);
	
	/**
	 * 根据用户名和密码查询CommerceUser对象
	 * <p>
	 * select * from t_commerce_user where username= ? and password= ?
	 */
	CommerceUser findByUsernameAndPassword(String username, String password);
}
