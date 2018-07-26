package org.cloud.db.shop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "shop_notice")
public class Notice {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="to_user")
	private String toUser;
	
	private String content;
	
	@Column(name="from_user")
	private String fromUser;
	
	@Column(name="is_readed")
	private int isReaded;
	
	private Date create_date;
	
	private Date read_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public int getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(int isReaded) {
		this.isReaded = isReaded;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getRead_date() {
		return read_date;
	}

	public void setRead_date(Date read_date) {
		this.read_date = read_date;
	}
}
