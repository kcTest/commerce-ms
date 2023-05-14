package com.zkc.commerce.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 主键ids
 */
@Schema(description = "通用id对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableId {
	
	@Schema(description = "数据表记录主键")
	private List<Id> ids;
	
	@Schema(description = "数据表记录主键对象")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Id {
		
		@Schema(description = "数据表记录主键")
		private Long id;
	}
}
