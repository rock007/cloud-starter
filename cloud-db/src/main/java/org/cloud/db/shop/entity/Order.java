package org.cloud.db.shop.entity;

import org.cloud.db.shop.enumType.OrderStatusType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/***
 * 
 * @author 下午1:47:58 by sam
 *
 */
@Entity
@Table(name = "shop_orders")
public class Order {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long order_product_id;
	
	private Integer product_num;
	
	@Column(name="price",precision=10,scale=2)
	private float price;
	
	@Column(name="order_status")
	@Enumerated(EnumType.STRING)
	private OrderStatusType orderStatus;
	
	private String address_user;
	
	private String address_phone;
	
	private String address_full;
	
	/***
	 * 产品属性
	 */
	private String attrs_text;
	
	@Column(name="create_user")
	private String createUser;
	
	private Date create_date;
	
	private Date update_date;

	private String remarks;
	
	/**
	 * 完成后订单不可修改,1:订单完成，-1：订单取消（超时，或手动取消），-2：订单退货
	 */
	private Integer is_complete;
	
	@Column(name="order_code")
	private String orderCode;
	
	@OneToOne
	@JoinColumn(name="order_product_id",updatable=false,insertable=false)
	private Order_Product orderProduct=new Order_Product();
	
	@Transient
	private String orderStatuStr;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getOrder_product_id() {
		return order_product_id;
	}

	public void setOrder_product_id(Long order_product_id) {
		this.order_product_id = order_product_id;
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

	public OrderStatusType getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatusType orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getAddress_user() {
		return address_user;
	}

	public void setAddress_user(String address_user) {
		this.address_user = address_user;
	}

	public String getAddress_full() {
		return address_full;
	}

	public void setAddress_full(String address_full) {
		this.address_full = address_full;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Order_Product getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(Order_Product orderProduct) {
		this.orderProduct = orderProduct;
	}

	public String getAddress_phone() {
		return address_phone;
	}

	public void setAddress_phone(String address_phone) {
		this.address_phone = address_phone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getIs_complete() {
		return is_complete;
	}

	public void setIs_complete(Integer is_complete) {
		this.is_complete = is_complete;
	}

	public String getAttrs_text() {
		return attrs_text;
	}

	public void setAttrs_text(String attrs_text) {
		this.attrs_text = attrs_text;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderStatuStr() {
	
		return this.orderStatus.getName();
	}
	
}
