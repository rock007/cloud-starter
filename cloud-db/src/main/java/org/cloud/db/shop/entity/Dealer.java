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
 * 经销商
 * @author 上午11:42:41 by sam
 *
 */
@Entity
@Table(name = "shop_dealer")
public class Dealer {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String city;
	
	private String area;
	
	private String link_man;
	
	private String link_mobile;
	
	private String address;
	
	private Integer status;
	
	@Column(name="main_user")
	private String mainUser;
	
	private Date create_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getLink_man() {
		return link_man;
	}

	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}

	public String getLink_mobile() {
		return link_mobile;
	}

	public void setLink_mobile(String link_mobile) {
		this.link_mobile = link_mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getMainUser() {
		return mainUser;
	}

	public void setMainUser(String mainUser) {
		this.mainUser = mainUser;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
	
}
