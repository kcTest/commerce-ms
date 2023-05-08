package com.zkc.commerce;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA 非对称加密算法 生成公钥和私钥
 */
@Slf4j
@SpringBootTest
public class RSATest {
	
	@Test
	public void generateKeyBytes() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		//生成公钥和私钥对
		KeyPair keyPair = generator.generateKeyPair();
		//获取公钥和私钥对象
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		log.info("公钥：[{}]", Base64.encode(publicKey.getEncoded()));
		log.info("私钥：[{}]", Base64.encode(privateKey.getEncoded()));
	}
}
