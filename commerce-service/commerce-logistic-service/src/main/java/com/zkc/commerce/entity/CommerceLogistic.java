package com.zkc.commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 物流表实体
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_commerce_logistic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommerceLogistic {
	
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
	 * 订单id
	 */
	@Column(name = "order_id", nullable = false)
	private Long orderId;
	
	/**
	 * 地址表记录id
	 */
	@Column(name = "address_id", nullable = false)
	private Long addressId;
	
	/**
	 * 备注信息(json存储)
	 */
	@Column(name = "extra_info", nullable = false)
	private String extraInfo;
	
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
	
	public CommerceLogistic(Long userId, Long orderId, Long addressId, String extraInfo) {
		this.userId = userId;
		this.orderId = orderId;
		this.addressId = addressId;
		this.extraInfo = StringUtils.hasText(extraInfo) ? extraInfo : "{}";
	}
	
}
