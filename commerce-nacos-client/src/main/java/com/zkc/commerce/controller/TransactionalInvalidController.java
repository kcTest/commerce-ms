package com.zkc.commerce.controller;

import com.zkc.commerce.service.TransactionalInvalidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("transactional-invalid")
@RequiredArgsConstructor
public class TransactionalInvalidController {
	
	private final TransactionalInvalidService transactionalInvalidService;
	
	/**
	 * 3. rollbackFor属性设置错误
	 */
	@GetMapping("/wrong-rollback-for-01")
	public void wrongRollbackFor01() throws Exception {
		log.info("进入 wrongRollbackFor01 方法调用");
		transactionalInvalidService.wrongRollbackFor01();
	}
	
	/**
	 * 3. rollbackFor属性设置错误
	 */
	@GetMapping("/wrong-rollback-for-02")
	public void wrongRollbackFor02() throws Exception {
		log.info("进入 wrongRollbackFor02 方法调用");
		transactionalInvalidService.wrongRollbackFor02();
	}
	
	/**
	 * 4. 在同一个类中的方法调用，导致事务失效
	 */
	@GetMapping("/wrong-inner-call")
	public void wrongInnerCall() throws Exception {
		log.info("进入 wrongInnerCall 方法调用");
		transactionalInvalidService.wrongInnerCall();
	}
	
	/**
	 * 5. 自己主动去catch,代表[没有出现]异常，导致事务失效
	 */
	@GetMapping("/wrong-try-catch")
	public void wrongTryCatch() throws Exception {
		log.info("进入 wrongTryCatch 方法调用");
		transactionalInvalidService.wrongTryCatch();
	}
}
