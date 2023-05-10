package com.zkc.commerce.filter;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.constant.CommonConstant;
import com.zkc.commerce.constant.GatewayConstant;
import com.zkc.commerce.util.TokenParseUtil;
import com.zkc.commerce.vo.JwtToken;
import com.zkc.commerce.vo.LoginUserInfo;
import com.zkc.commerce.vo.UsernameAndPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 全局登录鉴权过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {
	
	/**
	 * 注册中心客户端 可以从注册中心获取服务实例信息 可以直接注入
	 */
	private final LoadBalancerClient loadBalancerClient;
	
	private final RestTemplate restTemplate;
	
	/**
	 * 登录 注册时得到token并返回
	 * <p>
	 * 其它接口的访问则进行鉴权
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		
		//1登录
		if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)) {
			String token = getTokenFromAuthCenter(request, GatewayConstant.AUTH_CENTER_TOKEN_URL_FORMAT);
			//header中不能设置null
			response.getHeaders().add(CommonConstant.JET_USER_INFO_KEY, token == null ? "null" : token);
			response.setStatusCode(HttpStatus.OK);
			return response.setComplete();
		}
		
		//2注册
		if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
			String token = getTokenFromAuthCenter(request, GatewayConstant.AUTH_CENTER_REGISTER_URL_FORMAT);
			//header中不能设置null
			response.getHeaders().add(CommonConstant.JET_USER_INFO_KEY, token == null ? "null" : token);
			response.setStatusCode(HttpStatus.OK);
			return response.setComplete();
		}
		
		//3 访问其它服务接口 校验token
		HttpHeaders headers = request.getHeaders();
		String token = headers.getFirst(CommonConstant.JET_USER_INFO_KEY);
		LoginUserInfo loginUserInfo = null;
		try {
			loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
		} catch (Exception e) {
			log.error("解析用户token信息错误:[{}]", e.getMessage(), e);
		}
		
		//返回401
		if (loginUserInfo == null) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.setComplete();
		}
		
		//解析通过放行
		return chain.filter(exchange);
	}
	
	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 2;
	}
	
	/**
	 * 从授权中心获取token
	 *
	 * @param uriFormat 登录或注册
	 */
	private String getTokenFromAuthCenter(ServerHttpRequest request, String uriFormat) {
		//负载均衡
		ServiceInstance serviceInstance = loadBalancerClient.choose(
				CommonConstant.AUTH_CENTER_SERVICE_ID
		);
		log.info("获取注册到Nacos的客户端信息(当前为授权中心服务)：[{}],[{}],[{}]", serviceInstance.getServiceId(),
				serviceInstance.getInstanceId(), JSON.toJSONString(serviceInstance.getMetadata()));
		
		String requestUrl = String.format(uriFormat, serviceInstance.getHost(), serviceInstance.getPort());
		UsernameAndPassword requestBody =
				JSON.parseObject(parseBodyFromRequest(request), UsernameAndPassword.class);
		log.info("登录请求的请求路径和请求体: [{}],[{}]", requestUrl, requestBody);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JwtToken jwtToken = restTemplate.postForObject(
				requestUrl,
				new HttpEntity<>(JSON.toJSONString(requestBody), headers),
				JwtToken.class
		);
		
		if (jwtToken != null) {
			return jwtToken.getToken();
		}
		
		return null;
	}
	
	/**
	 * 从post请求中获取请求数据
	 */
	private String parseBodyFromRequest(ServerHttpRequest request) {
		//获取请求体
		Flux<DataBuffer> body = request.getBody();
		AtomicReference<String> bodyRef = new AtomicReference<>();
		
		//订阅缓冲区去消费请求体中的数据
		body.subscribe(buffer -> {
			ByteBuffer copy = ByteBuffer.allocate(buffer.readableByteCount());
			buffer.toByteBuffer(copy);
			CharBuffer charBuffer = StandardCharsets.UTF_8.decode(copy);
			//释放之前保存的缓冲区
			DataBufferUtils.release(buffer);
			bodyRef.set(charBuffer.toString());
		});
		
		//获取requestBody;
		return bodyRef.get();
	}
}
