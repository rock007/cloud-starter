package org.cloud.db.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "shop_product_attr")
public class ProductAttr {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="product_id")
	private Long productId;
	
	private String attr_name;
	
	private String attr_value;
	
	private Integer has_price;
	
	@Column(name="price_normal",precision=10,scale=2) 
	private float price_normal;
	
	@Column(name="price_now",precision=10,scale=2) 
	private float price_now;
	
	/***
	 * 库存
	 */
	private Integer stock;
	
	/**
	 * 销售数量
	 */
	private Integer sales_num;

	private int status;

	@Column(name="create_user")
	private String createUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public String getAttr_value() {
		return attr_value;
	}

	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}

	public float getPrice_normal() {
		return price_normal;
	}

	public void setPrice_normal(float price_normal) {
		this.price_normal = price_normal;
	}

	public float getPrice_now() {
		return price_now;
	}

	public void setPrice_now(float price_now) {
		this.price_now = price_now;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getHas_price() {
		return has_price;
	}

	public void setHas_price(Integer has_price) {
		this.has_price = has_price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getSales_num() {
		return sales_num;
	}

	public void setSales_num(Integer sales_num) {
		this.sales_num = sales_num;
	}
	
	
}
