package com.zkc.commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表 实体类定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_commerce_user")
public class CommerceUser implements Serializable {
	
	/**
	 * 自增主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	/**
	 * 用户名
	 */
	@Column(name = "username", nullable = false)
	private String username;
	
	/**
	 * MD5密码
	 */
	@Column(name = "password", nullable = false)
	private String password;
	
	/**
	 * 额外信息 json
	 */
	@Column(name = "extra_info", nullable = false)
	private String extraInfo;
	
	/**
	 * 创建时间 填充
	 */
	@CreatedDate
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	/**
	 * 修改时间 填充
	 */
	@LastModifiedDate
	@Column(name = "update_time", nullable = false)
	private Date updateTime;
	
}
