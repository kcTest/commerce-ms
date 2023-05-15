CREATE TABLE `t_commerce_goods` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                    `goods_category` varchar(64) NOT NULL DEFAULT '' COMMENT '商品类别',
                                    `brand_category` varchar(64) NOT NULL DEFAULT '' COMMENT '品牌分类',
                                    `goods_name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
                                    `goods_pic` varchar(64) NOT NULL DEFAULT '' COMMENT '商品图片',
                                    `goods_description` varchar(512) NOT NULL DEFAULT '' COMMENT '商品描述信息',
                                    `goods_status` varchar(64) NOT NULL DEFAULT '' COMMENT '商品状态',
                                    `price` int NOT NULL DEFAULT '0' COMMENT '商品价格',
                                    `supply` bigint NOT NULL DEFAULT '0' COMMENT '总供应量',
                                    `inventory` bigint NOT NULL DEFAULT '0' COMMENT '库存',
                                    `goods_property` varchar(1024) NOT NULL DEFAULT '' COMMENT '商品属性',
                                    `create_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '创建时间',
                                    `update_time` datetime NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `goods_category_brand_name` (`goods_category`,`brand_category`,`goods_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='商品表';