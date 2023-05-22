package com.zkc.commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "springboot_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaSpringBootUser {
	
	/**
	 * 主键id
	 */
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * 用户名
	 */
	@Basic
	@Column(name = "user_name", nullable = false)
	private String username;
	
}
