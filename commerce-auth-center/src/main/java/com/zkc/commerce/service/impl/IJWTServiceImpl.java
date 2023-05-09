package com.zkc.commerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.constant.AuthConstant;
import com.zkc.commerce.constant.CommonConstant;
import com.zkc.commerce.dao.CommerceUserDao;
import com.zkc.commerce.entity.CommerceUser;
import com.zkc.commerce.service.IJWTService;
import com.zkc.commerce.vo.LoginUserInfo;
import com.zkc.commerce.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * jwt相关服务接口实现
 * 任意类型异常回滚
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class IJWTServiceImpl implements IJWTService {
	
	private final CommerceUserDao commerceUserDao;
	
	@Override
	public String generateToken(String username, String password) throws Exception {
		return generateToken(username, password, 0);
	}
	
	@Override
	public String generateToken(String username, String password, int expire) throws Exception {
		//验证用户名和密码(md5形式)
		CommerceUser commerceUser = commerceUserDao.findByUsernameAndPassword(username, password);
		if (commerceUser == null) {
			log.error("找不到用户:[{}],[{}]", username, password);
			return null;
		}
		
		//payload中的信息
		LoginUserInfo loginUserInfo = new LoginUserInfo(commerceUser.getId(), commerceUser.getUsername());
		if (expire <= 0) {
			expire = AuthConstant.DEFAULT_EXPIRE_DAY;
		}
		
		//计算超时时间
		ZonedDateTime zonedDateTime = LocalDate.now()
				.plus(expire, ChronoUnit.DAYS)
				.atStartOfDay(ZoneId.systemDefault());
		Date expireDate = Date.from(zonedDateTime.toInstant());
		
		return Jwts.builder()
				//填充payload K,V
				.claim(CommonConstant.JET_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
				.setId(UUID.randomUUID().toString())
				.setExpiration(expireDate)
				//指定私钥和加密算法 
				.signWith(getPrivateKey(), SignatureAlgorithm.RS256)
				//生成jwt的三部分， 签名部分使用指定的私钥和算法生成
				.compact();
	}
	
	@Override
	public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {
		//校验用户名和密码
		CommerceUser oldUser = commerceUserDao.findByUsername(usernameAndPassword.getUsername());
		if (oldUser != null) {
			log.error("用户名已存在,[{}]", oldUser.getUsername());
			return null;
		}
		
		//注册新用户
		CommerceUser newUser = new CommerceUser();
		newUser.setUsername(usernameAndPassword.getUsername());
		newUser.setPassword(usernameAndPassword.getPassword());
		newUser.setExtraInfo("{}");
		newUser = commerceUserDao.save(newUser);
		log.info("用户注册成功:[{}],[{}]", newUser.getUsername(), newUser.getId());
		
		//生成token
		return generateToken(newUser.getUsername(), newUser.getPassword());
	}
	
	/**
	 * 根据本地存储的私钥获取PrivateKey对象
	 */
	private PrivateKey getPrivateKey() throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(AuthConstant.PRIVATE_KEY));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(priPKCS8);
	}
	
}
