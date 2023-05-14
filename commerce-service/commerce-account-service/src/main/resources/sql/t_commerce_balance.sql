CREATE TABLE `t_commerce_balance` (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                      `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
                                      `balance` bigint NOT NULL DEFAULT '0' COMMENT '用户余额',
                                      `create_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '创建时间',
                                      `update_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '更新时间',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `user_id_key` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='用户账户余额表';