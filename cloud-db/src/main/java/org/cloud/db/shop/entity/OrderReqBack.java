package org.cloud.db.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "shop_order_reqback")
public class OrderReqBack {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="order_id")
	private Long orderId;
	
	private Integer is_received;
	
	private Long ems_id;
	
	private String req_text;
	
	private String answer_text;
	
	private Integer status;
	
	private Date create_date;
	
	private Date update_date;
	
	@OneToOne
	@JoinColumn(name="id",updatable=false,insertable=false)
	private GoodsPackage goodsEms;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getIs_received() {
		return is_received;
	}

	public void setIs_received(Integer is_received) {
		this.is_received = is_received;
	}

	public Long getEms_id() {
		return ems_id;
	}

	public void setEms_id(Long ems_id) {
		this.ems_id = ems_id;
	}

	public String getReq_text() {
		return req_text;
	}

	public void setReq_text(String req_text) {
		this.req_text = req_text;
	}

	public String getAnswer_text() {
		return answer_text;
	}

	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public GoodsPackage getGoodsEms() {
		return goodsEms;
	}

	public void setGoodsEms(GoodsPackage goodsEms) {
		this.goodsEms = goodsEms;
	}
	
	
}
