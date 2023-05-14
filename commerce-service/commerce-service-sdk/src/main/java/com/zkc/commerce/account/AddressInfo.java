package com.zkc.commerce.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 用户地址信息
 */
@Schema(description = "用户地址信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {
	
	@Schema(description = "地址所属用户id")
	private Long userId;
	
	@Schema(description = "地址详细信息")
	private List<AddressItem> addressItems;
	
	/**
	 * 可以包含多个地址信息
	 */
	@Schema(description = "用户单个地址信息")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddressItem {
		
		@Schema(description = "地址表主键id")
		private Long id;
		
		@Schema(description = "用户姓名")
		private String username;
		
		@Schema(description = "电话")
		private String phone;
		
		@Schema(description = "省")
		private String province;
		
		@Schema(description = "市")
		private String city;
		
		@Schema(description = "详细地址")
		private String addressDetail;
		
		@Schema(description = "创建时间")
		private Date createTime;
		
		@Schema(description = "更新时间")
		private Date updateTime;
		
		public AddressItem(Long id) {
			this.id = id;
		}
		
		/**
		 * 将AddressItem转换为UserAddress;
		 */
		public UserAddress toUserAddress() {
			UserAddress userAddress = new UserAddress();
			userAddress.setUsername(this.username);
			userAddress.setPhone(this.phone);
			userAddress.setProvince((this.province));
			userAddress.setCity(this.city);
			userAddress.setAddressDetail(this.addressDetail);
			return userAddress;
		}
		
	}
	
}
