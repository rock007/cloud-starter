package org.cloud.db.cms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.cloud.db.sys.entity.UploadFile;

@Entity
@Table(name = "cms_articles")
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
 	private Long id;
	
	@NotNull
	@Size(min=2, max=256)
	private String title;

	@NotNull
	@Size(min=2, max=512)
	private String short_content;
	
	@NotNull
	private String content_html;
	
	@Size(min=0, max=100)
	private String source;
	
	@Size(min=0, max=512)
	private String source_url;
	
	private String keywords;
	
	private Integer status;
	
	private Integer view_num;
	
	@Column(name = "create_user")
	private String createUser;
	
	private Date create_date;
	
	private Date update_date;
	
	@Column(name = "cate_id")
	private Integer cateId;
	
	@Column(name = "order_index")
	private Integer orderIndex;
	
	@Transient
	private List<ArticleImage> Images;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShort_content() {
		return short_content;
	}

	public void setShort_content(String short_content) {
		this.short_content = short_content;
	}

	public String getContent_html() {
		return content_html;
	}

	public void setContent_html(String content_html) {
		this.content_html = content_html;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getView_num() {
		return view_num;
	}

	public void setView_num(Integer view_num) {
		this.view_num = view_num;
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

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public List<ArticleImage> getImages() {
		return Images;
	}

	public void setImages(List<ArticleImage> images) {
		Images = images;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	
	
	
}
