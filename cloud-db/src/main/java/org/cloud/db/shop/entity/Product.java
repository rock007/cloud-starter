package org.cloud.db.shop.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "shop_product")
public class Product {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	@Column(name="cate_name")
	private String cateName;
	
	private String keywords;
	
	private String shortwords;
	
	@Column(name="price_normal",precision=10,scale=2)  
	private float price_normal;
	
	@Column(name="price_now",precision=10,scale=2)  
	private float price_now;
	
	private int is_discount;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column(name="html_desc", columnDefinition="CLOB", nullable=true) 
	private String html_desc;
	
	private String desc_img;
	/***
	 * 库存
	 */
	private Integer stock;
	
	/**
	 * 销售数量
	 */
	private Integer sales_num;
	
	/**
	 * 浏览数
	 */
	private Integer view_num;
	
	/**
	 * 状态：1销售中;0待销售;2下架;-1删除;
	 */
	private Integer status;
	
	@Column(name="dealer_id")
	private Long dealerId;
	
	//@JsonIgnore
	private String create_user;

	private Date create_date;
	
	private Date update_date;
	
	@OneToMany(fetch=FetchType.LAZY)//cascade=CascadeType.REMOVE
	@JoinColumn(name="product_id",updatable=false,insertable=false)
	private Set<ProductAttr> attrs=new HashSet<ProductAttr>();
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id",updatable=false,insertable=false)
	private Set<ProductArg> args=new HashSet<ProductArg>();
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id",updatable=false,insertable=false)
	private Set<ProductImage> images=new HashSet<ProductImage>();
	
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getShortwords() {
		return shortwords;
	}

	public void setShortwords(String shortwords) {
		this.shortwords = shortwords;
	}


	public int getIs_discount() {
		return is_discount;
	}

	public void setIs_discount(int is_discount) {
		this.is_discount = is_discount;
	}

	public String getDesc_img() {
		return desc_img;
	}

	public void setDesc_img(String desc_img) {
		this.desc_img = desc_img;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}


	public String getHtml_desc() {
		return html_desc;
	}

	public void setHtml_desc(String html_desc) {
		this.html_desc = html_desc;
	}

	public float getPrice_normal() {
		return price_normal;
	}

	public void setPrice_normal(float price_normal) {
		this.price_normal = price_normal;
	}

	public float getPrice_now() {
		return price_now;
	}

	public void setPrice_now(float price_now) {
		this.price_now = price_now;
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

	public Set<ProductAttr> getAttrs() {
		return attrs;
	}

	public void setAttrs(Set<ProductAttr> attrs) {
		this.attrs = attrs;
	}

	public Set<ProductArg> getArgs() {
		return args;
	}

	public void setArgs(Set<ProductArg> args) {
		this.args = args;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getSales_num() {
		return sales_num;
	}

	public void setSales_num(Integer sales_num) {
		this.sales_num = sales_num;
	}

	public Integer getView_num() {
		return view_num;
	}

	public void setView_num(Integer view_num) {
		this.view_num = view_num;
	}
	
	
}
