package com.zkc.commerce.entity;

import com.alibaba.fastjson.JSON;
import com.zkc.commerce.constant.BrandCategory;
import com.zkc.commerce.constant.GoodsCategory;
import com.zkc.commerce.constant.GoodsStatus;
import com.zkc.commerce.converter.BrandCategoryConverter;
import com.zkc.commerce.converter.GoodsCategoryConverter;
import com.zkc.commerce.converter.GoodsStatusConverter;
import com.zkc.commerce.goods.GoodsInfo;
import com.zkc.commerce.goods.SimpleGoodsInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_commerce_goods")
public class CommerceGoods {
	
	/**
	 * 自增主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	/**
	 * 商品类别
	 */
	@Convert(converter = GoodsCategoryConverter.class)
	@Column(name = "goods_category", nullable = false)
	private GoodsCategory goodsCategory;
	
	/**
	 * 品牌分类
	 */
	@Convert(converter = BrandCategoryConverter.class)
	@Column(name = "brand_category", nullable = false)
	private BrandCategory brandCategory;
	
	/**
	 * 商品名称
	 */
	@Column(name = "goods_name", nullable = false)
	private String goodsName;
	
	/**
	 * 商品图片
	 */
	@Column(name = "goods_pic", nullable = false)
	private String goodsPic;
	
	/**
	 * 商品描述信息
	 */
	@Column(name = "goods_description", nullable = false)
	private String goodsDescription;
	
	/**
	 * 商品状态
	 */
	@Convert(converter = GoodsStatusConverter.class)
	@Column(name = "goods_status", nullable = false)
	private GoodsStatus goodsStatus;
	
	/**
	 * 商品价格： 单位 分
	 */
	@Column(name = "price", nullable = false)
	private Integer price;
	
	/**
	 * 总供应量
	 */
	@Column(name = "supply", nullable = false)
	private Long supply;
	
	/**
	 * 库存
	 */
	@Column(name = "inventory", nullable = false)
	private Long inventory;
	
	/**
	 * 商品属性
	 */
	@Column(name = "goods_property", nullable = false)
	private String goodsProperty;
	
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
	 * 将CommerceGoods转换为实体对象
	 */
	public static CommerceGoods to(GoodsInfo goodsInfo) {
		CommerceGoods commerceGoods = new CommerceGoods();
		commerceGoods.setGoodsCategory(GoodsCategory.of(goodsInfo.getGoodsCategory()));
		commerceGoods.setBrandCategory(BrandCategory.of(goodsInfo.getBrandCategory()));
		commerceGoods.setGoodsName(goodsInfo.getGoodsName());
		commerceGoods.setGoodsPic(goodsInfo.getGoodsPic());
		commerceGoods.setGoodsDescription(goodsInfo.getGoodsDescription());
		commerceGoods.setPrice(goodsInfo.getPrice());
		commerceGoods.setGoodsStatus(GoodsStatus.ONLINE);
		commerceGoods.setPrice(goodsInfo.getPrice());
		commerceGoods.setSupply(goodsInfo.getSupply());
		commerceGoods.setInventory(goodsInfo.getSupply());
		commerceGoods.setGoodsProperty(JSON.toJSONString(goodsInfo.getGoodsProperty()));
		return commerceGoods;
	}
	
	/**
	 * 将实体对象转成GoodsInfo对象
	 */
	public GoodsInfo toGoodsInfo() {
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setId(this.id);
		goodsInfo.setGoodsCategory(this.goodsCategory.getCode());
		goodsInfo.setBrandCategory(this.brandCategory.getCode());
		goodsInfo.setGoodsName(this.goodsName);
		goodsInfo.setGoodsPic(this.goodsPic);
		goodsInfo.setGoodsDescription(this.goodsDescription);
		goodsInfo.setGoodsStatus(this.goodsStatus.getStatus());
		goodsInfo.setGoodsProperty(JSON.parseObject(this.goodsProperty, GoodsInfo.GoodsProperty.class));
		goodsInfo.setSupply(this.supply);
		goodsInfo.setInventory(this.inventory);
		goodsInfo.setCreateTime(this.createTime);
		goodsInfo.setUpdateTime(this.updateTime);
		return goodsInfo;
	}
	
	/**
	 * 将实体类对象转成SimpleGoodsInfo对象
	 */
	public SimpleGoodsInfo toSimpleGoodsInfo() {
		SimpleGoodsInfo simpleGoodsInfo = new SimpleGoodsInfo();
		simpleGoodsInfo.setId(this.id);
		simpleGoodsInfo.setGoodsName(this.goodsName);
		simpleGoodsInfo.setGoodsPic(this.goodsPic);
		simpleGoodsInfo.setPrice(this.price);
		return simpleGoodsInfo;
	}
	
}
