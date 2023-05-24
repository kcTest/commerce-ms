package com.zkc.commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners((AuditingEntityListener.class))
@Table(name = "t_commerce_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommerceOrder {
	
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
	 * 用户地址记录id
	 */
	@Column(name = "address_id", nullable = false)
	private Long addressId;
	
	/**
	 * 订单详细信息 json存储
	 */
	@Column(name = "order_detail", nullable = false)
	private String orderDetail;
	
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
	
	public CommerceOrder(Long userId, Long addressId, String orderDetail) {
		this.userId = userId;
		this.addressId = addressId;
		this.orderDetail = orderDetail;
	}
	
}
