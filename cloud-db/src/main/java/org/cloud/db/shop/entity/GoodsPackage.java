package org.cloud.db.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 货物包裹（快递信息）
 * @author 下午3:59:49 by sam
 *
 */
@Entity
@Table(name = "shop_package")
public class GoodsPackage {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="order_id")
	private Long orderId;
	
	private String ems_name;
	
	private String ems_no;
	
	private String ems_logs;
	
	private Integer status;
	
	private Date create_date;
	
	private Date update_date;

	private Integer flag;
	
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

	public String getEms_name() {
		return ems_name;
	}

	public void setEms_name(String ems_name) {
		this.ems_name = ems_name;
	}

	public String getEms_no() {
		return ems_no;
	}

	public void setEms_no(String ems_no) {
		this.ems_no = ems_no;
	}

	public String getEms_logs() {
		return ems_logs;
	}

	public void setEms_logs(String ems_logs) {
		this.ems_logs = ems_logs;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	

}
