package org.cloud.db.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "shop_order_product")
public class Order_Product {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String cate_name;
	
	private String keywords;
	
	private String shotwords;
	
	@Column(name="price_normal",precision=10,scale=2) 
	private float price_normal;
	
	@Column(name="price_now",precision=10,scale=2) 
	private float price_now;
	
	private Integer is_discount;
	
	private String html_desc;
	
	private String desc_img;
	
	private Long dealer_id;
	
	private String attr_name;
	
	private String attr_value;
	
	@Column(name="attr_price_normal",precision=10,scale=2) 
	private float attr_price_normal;
	
	@Column(name="attr_price_now",precision=10,scale=2) 
	private float attr_price_now;
	
	private Date create_date;
	
	private String create_user;
	
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="product_update")
	private Date productUpdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getShotwords() {
		return shotwords;
	}

	public void setShotwords(String shotwords) {
		this.shotwords = shotwords;
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

	public Integer getIs_discount() {
		return is_discount;
	}

	public void setIs_discount(Integer is_discount) {
		this.is_discount = is_discount;
	}

	public String getHtml_desc() {
		return html_desc;
	}

	public void setHtml_desc(String html_desc) {
		this.html_desc = html_desc;
	}

	public String getDesc_img() {
		return desc_img;
	}

	public void setDesc_img(String desc_img) {
		this.desc_img = desc_img;
	}

	public Long getDealer_id() {
		return dealer_id;
	}

	public void setDealer_id(Long dealer_id) {
		this.dealer_id = dealer_id;
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

	public float getAttr_price_normal() {
		return attr_price_normal;
	}

	public void setAttr_price_normal(float attr_price_normal) {
		this.attr_price_normal = attr_price_normal;
	}

	public float getAttr_price_now() {
		return attr_price_now;
	}

	public void setAttr_price_now(float attr_price_now) {
		this.attr_price_now = attr_price_now;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Date getProductUpdate() {
		return productUpdate;
	}

	public void setProductUpdate(Date productUpdate) {
		this.productUpdate = productUpdate;
	}	
	
	
}
