package org.cloud.db.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "shop_product_args")
public class ProductArg {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="product_id")
	private Long productId;
	
	private String arg_name;
	
	private String arg_text;

	private String create_user;

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

	public String getArg_name() {
		return arg_name;
	}

	public void setArg_name(String arg_name) {
		this.arg_name = arg_name;
	}

	public String getArg_text() {
		return arg_text;
	}

	public void setArg_text(String arg_text) {
		this.arg_text = arg_text;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
}
