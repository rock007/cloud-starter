package org.cloud.db.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/***
 * 退款
 * @author 下午3:17:06 by sam
 *
 */
@Entity
@Table(name = "shop_pay_refund")
public class PayRefund {
	
	@Id
	private String batch_no;
	
	@Column(name="order_id")
	private long orderId;
	
	@Column(name="refund_money",precision=10,scale=2)
	private float refund_money;
	
	private String trade_no;
	
	private String remarks;

	private String refund_status;
	
	private Date create_date;
	
	private Date update_date;

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public float getRefund_money() {
		return refund_money;
	}

	public void setRefund_money(float refund_money) {
		this.refund_money = refund_money;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
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

	

}
