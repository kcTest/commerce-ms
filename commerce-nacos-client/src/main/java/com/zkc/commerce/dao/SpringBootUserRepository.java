package com.zkc.commerce.dao;

import com.zkc.commerce.entity.JpaSpringBootUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringBootUserRepository extends JpaRepository<JpaSpringBootUser, Integer> {
}
