package org.cloud.shop.model;

import org.cloud.db.shop.entity.Order;

public class SubmitOrderModel extends Order {

	private Long productId;
	
	private String attr_name;
	
	private String attr_value;
	
	private float attr_price_normal;
	
	private float attr_price_now;

	private Long cartId;
	
	private String pay_type;
	
	private String remarks;
	
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

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
