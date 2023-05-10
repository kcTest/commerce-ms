package com.zkc.commerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * predicate使用
 * <p>
 * PathRoutePredicateFactory
 */
@Slf4j
@SpringBootTest
public class PredicateTest {
	
	public static List<String> MICRO_SERVICE = Arrays.asList(
			"nacos", "auth", "gateway", "openfeign"
	);
	
	/**
	 * test方法主要用于参数符不符合规则 返回bool
	 */
	@Test
	public void testPredicateTest() {
		Predicate<String> letterLengthLimit = s -> s.length() > 5;
		MICRO_SERVICE.stream().filter(letterLengthLimit).forEach(
				System.out::println
		);
	}
	
	/**
	 * and方法 等同于逻辑与 && 需要所有条件都满足
	 */
	@Test
	public void testPredicateAnd() {
		Predicate<String> letterLengthLimit = s -> s.length() > 5;
		Predicate<String> letterStartWith = s -> s.startsWith("gate");
		MICRO_SERVICE.stream().filter(letterLengthLimit.and(letterStartWith)
		).forEach(System.out::println);
	}
	
	/**
	 * or 等同于逻辑或 || 只要满足一个条件
	 */
	@Test
	public void testPredicateOr() {
		Predicate<String> letterLengthLimit = s -> s.length() > 5;
		Predicate<String> letterStartWith = s -> s.startsWith("gate");
		MICRO_SERVICE.stream().filter(letterLengthLimit.or(letterStartWith)
		).forEach(System.out::println);
	}
	
	/**
	 * negate 等同于逻辑非 !
	 */
	@Test
	public void testPredicateNegate() {
		Predicate<String> letterStartWith = s -> s.startsWith("gate");
		MICRO_SERVICE.stream().filter(letterStartWith.negate()).forEach(System.out::println);
	}
	
	/**
	 * isEqual 类似于 equals()，区别：先判断对象是否为NULL,不为NULL在使用equals方法比较
	 */
	@Test
	public void testPredicateIsEqual() {
		Predicate<String> equalGateway = s -> Predicate.isEqual("gateway").test(s);
		MICRO_SERVICE.stream().filter(equalGateway).forEach(System.out::println);
	}
}
