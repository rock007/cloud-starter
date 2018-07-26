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
 * 
 * @author 上午9:43:59 by sam
 *
 */
@Entity
@Table(name = "shop_account")
public class ShopUser {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String username;

	private String pass;
	
	private String realname;
	
	private String sex;
	
	private String telphone;
	
	private String location_x;
	
	private String location_y;
	
	private String location_z;
	
	private String location_area;
	
	private String role;
	
	private String email;
	
	private String qq;
	
	private String weibo;
	
	private Date create_date;
	
	private String lastlogin_ip;
	
	private Date lastlogin_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getLocation_x() {
		return location_x;
	}

	public void setLocation_x(String location_x) {
		this.location_x = location_x;
	}

	public String getLocation_y() {
		return location_y;
	}

	public void setLocation_y(String location_y) {
		this.location_y = location_y;
	}

	public String getLocation_z() {
		return location_z;
	}

	public void setLocation_z(String location_z) {
		this.location_z = location_z;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getLastlogin_ip() {
		return lastlogin_ip;
	}

	public void setLastlogin_ip(String lastlogin_ip) {
		this.lastlogin_ip = lastlogin_ip;
	}

	public Date getLastlogin_date() {
		return lastlogin_date;
	}

	public void setLastlogin_date(Date lastlogin_date) {
		this.lastlogin_date = lastlogin_date;
	}

	public String getLocation_area() {
		return location_area;
	}

	public void setLocation_area(String location_area) {
		this.location_area = location_area;
	}
	
}
