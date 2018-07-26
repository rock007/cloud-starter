package org.cloud.db.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "shop_product_images")
public class ProductImage {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="product_id")
	private Long productId;
	
	private String src;
	
	private String alt;

	private Integer status;

	private Long upload_file_id;

	private String  create_user;

	private Integer postion;
	
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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUpload_file_id() {
		return upload_file_id;
	}

	public void setUpload_file_id(Long upload_file_id) {
		this.upload_file_id = upload_file_id;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Integer getPostion() {
		return postion;
	}

	public void setPostion(Integer postion) {
		this.postion = postion;
	}
	
	
	
}
