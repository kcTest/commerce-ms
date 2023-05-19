package com.zkc.commerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息传递对象, spring cloud stream + kafka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessage {
	
	private Integer id;
	
	private String content;
	
	private String version;
	
	private String projectName;
	
}
