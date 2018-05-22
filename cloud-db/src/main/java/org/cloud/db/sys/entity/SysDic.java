/**
*@Author: sam
*@Date: 2017年11月21日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.db.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_dict")
public class SysDic {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "dict_id")
	private Long dictId;
	
    @Column(name = "parent_id")
	private Long parentId;
	
	private String mkey;
	
	private String text;
	
	private Integer flag;
	
	private Date create_date;

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMkey() {
		return mkey;
	}

	public void setMkey(String mkey) {
		this.mkey = mkey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
}

