package com.zkc.commerce.entity;

import com.zkc.commerce.account.AddressInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * 用户地址表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_commerce_address")
public class CommerceAddress {
	
	/**
	 * 自增主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	/**
	 * 电话
	 */
	@Column(name = "phone", nullable = false)
	private String phone;
	
	/**
	 * 省
	 */
	@Column(name = "province", nullable = false)
	private String province;
	
	/**
	 * 市
	 */
	@Column(name = "city", nullable = false)
	private String city;
	
	/**
	 * 详细地址
	 */
	@Column(name = "address_detail", nullable = false)
	private String addressDetail;
	
	/**
	 * 创建时间
	 */
	@CreatedDate
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@LastModifiedDate
	@Column(name = "update_time", nullable = false)
	private Date updateTime;
	
	/**
	 * 根据userId + AddressItem 得到CommerceAddress
	 */
	public static CommerceAddress to(Long userId, AddressInfo.AddressItem addressItem) {
		CommerceAddress commerceAddress = new CommerceAddress();
		commerceAddress.setUserId(userId);
		commerceAddress.setUserName(addressItem.getUsername());
		commerceAddress.setPhone(addressItem.getPhone());
		commerceAddress.setProvince(addressItem.getProvince());
		commerceAddress.setCity(addressItem.getCity());
		commerceAddress.setAddressDetail(addressItem.getAddressDetail());
		return commerceAddress;
	}
	
	/**
	 * 将CommerceAddress对象转成AddressInfo
	 */
	public AddressInfo.AddressItem toAddressItem() {
		AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
		addressItem.setId(this.id);
		addressItem.setUsername(this.userName);
		addressItem.setPhone(this.phone);
		addressItem.setProvince(this.province);
		addressItem.setCity(this.getCity());
		addressItem.setAddressDetail(this.addressDetail);
		addressItem.setCreateTime(this.createTime);
		addressItem.setUpdateTime(this.updateTime);
		return addressItem;
	}
}
