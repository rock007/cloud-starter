package org.cloud.db.shop.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/***
 * 购物车
 * @author 上午11:36:32 by sam
 *
 */
@Entity
@Table(name = "shop_cart")
public class Cart {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="cart_uid")
	private String cartUid;
	
	@Column(name="product_id")
	private Long productId;
	
	private Integer product_num;
	
	private Long   attr_id ;
	private String   attr_name;
	private String    attr_value;

	@Column(name="attr_price_normal",precision=10,scale=2) 
	private float   attr_price_normal;
	   
	@Column(name="price",precision=10,scale=2) 
	private float price;
	
	@Column(name="create_user")
	private String createUser;
	
	private Date create_date;

	private Integer status;
	
	@OneToOne
	@JoinColumn(name="product_id",updatable=false,insertable=false)
	private Product product=new Product();
	
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

	public Integer getProduct_num() {
		return product_num;
	}

	public void setProduct_num(Integer product_num) {
		this.product_num = product_num;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getCartUid() {
		return cartUid;
	}

	public void setCartUid(String cartUid) {
		this.cartUid = cartUid;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(Long attr_id) {
		this.attr_id = attr_id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
