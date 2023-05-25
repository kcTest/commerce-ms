CREATE TABLE `t_commerce_logistic` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                       `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
                                       `order_id` bigint NOT NULL DEFAULT '0' COMMENT '订单id',
                                       `address_id` bigint NOT NULL DEFAULT '0' COMMENT '用户地址记录id',
                                       `extra_info` varchar(512) NOT NULL COMMENT '备注信息(json 存储)',
                                       `create_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '创建时间',
                                       `update_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '更新时间',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='物流表';