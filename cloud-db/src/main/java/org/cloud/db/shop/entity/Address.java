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
 * 送货地址
 * @author 上午11:42:27 by sam
 *
 */
@Entity
@Table(name = "shop_address")
public class Address {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String user_name;
	
	private String user_phone;
	
	private String province;
	
	private String city;
	
	private String area;

	private String rec_address;
	
	private Integer status;
	
	private Integer is_default;
	
	@Column(name="create_user")
	private String createUser;
	
	private Date create_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIs_default() {
		return is_default;
	}

	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getRec_address() {
		return rec_address;
	}

	public void setRec_address(String rec_address) {
		this.rec_address = rec_address;
	}
	
	
}
