package org.cloud.db.shop.service.imp;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.cloud.core.utils.DateUtil;
import org.cloud.db.shop.entity.Product;
import org.cloud.db.shop.entity.ProductArg;
import org.cloud.db.shop.entity.ProductAttr;
import org.cloud.db.shop.entity.ProductImage;
import org.cloud.db.shop.repository.ProductArgRepository;
import org.cloud.db.shop.repository.ProductAttrRepository;
import org.cloud.db.shop.repository.ProductImageRepository;
import org.cloud.db.shop.repository.ProductRepository;
import org.cloud.db.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductAttrRepository productAttrRepository;
	
	@Autowired
	private ProductArgRepository productArgRepository;
	
	@Autowired
	private ProductImageRepository productImageRepository;
	
	@Override
	public Product save(Product m) {

		Product oldOne=productRepository.findById(m.getId()).orElse(null);
		if(oldOne!=null){
			//编辑
			//oldOne.setCateName(cateName);
			
			//oldOne.setStatus(m.getStatus());
			m.setUpdate_date(new Date());
		}else{
			m.setCreate_date(new Date());
		}
		
	 	return productRepository.save(m);
	}
	
	@Override
	public ProductAttr saveAttr(ProductAttr m){
		
		if(m.getId()!=null){
			ProductAttr oldOne=productAttrRepository.findById(m.getId()).orElseThrow();
			
			oldOne.setAttr_name(m.getAttr_name());
			oldOne.setAttr_value(m.getAttr_value());
			oldOne.setPrice_normal(m.getPrice_normal());
			oldOne.setPrice_now(m.getPrice_now());
			oldOne.setStatus(m.getStatus());
		}
	 	return productAttrRepository.save(m);
	}
	
	public void delAttr(Long id){
		
		productAttrRepository.deleteById(id);
	}
	
	@Override
	public Page<Product> search(final Product m,int page,int pageSize,final  String beginDate,final  String endDate){
		
		return productRepository.findAll(new Specification<Product>() {

			public Predicate toPredicate(Root<Product> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				String title = m.getTitle();//产品名称

				String cateName = m.getCateName();//分类名
				String keywords = m.getKeywords(); //关键字
				
				Long dealerId = m.getDealerId(); //经销商
				Integer status = m.getStatus(); //状态
				
				String create_user=m.getCreate_user();
				Predicate p1,p2,p3,p4,p5,p6,p7,p8,pc;

				if (!StringUtils.isEmpty(title)) {
					p1 = cb.like(root.get("title").as(String.class), "%"
							+ title.trim().toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "1");
					pc=cb.and(p1);
				
				}
				
				if(!StringUtils.isEmpty(cateName)){
					
					p2 =cb.equal(root.get("cateName").as(String.class),cateName.trim());
					pc=cb.and(pc,p2);
				}

				if (!StringUtils.isEmpty(keywords)) {
					p3 = cb.like(root.get("keywords").as(String.class), "%"
							+ keywords.trim().toLowerCase() + "%");
					
					pc=cb.and(p3);

				}
				
				if(!StringUtils.isEmpty(status)){
	
					p4 =cb.equal(root.get("status").as(Integer.class),status);
					pc=cb.and(pc,p4);
				}
				
				if(!StringUtils.isEmpty(dealerId)){
					
					p5 =cb.equal(root.get("dealerId").as(Long.class),dealerId);
					pc=cb.and(pc,p5);
				}
				
				if(!StringUtils.isEmpty(beginDate)&&DateUtil.parseDateYYYYMMdd(beginDate)!=null){
					
					p6 =cb.greaterThan(root.get("create_date").as(Date.class),DateUtil.parseDateYYYYMMdd(beginDate));
					pc=cb.and(pc,p6);
				}
				
				if(!StringUtils.isEmpty(endDate)&&DateUtil.parseDateYYYYMMdd(endDate)!=null){
					
					p7 =cb.lessThanOrEqualTo(root.get("create_date").as(Date.class),DateUtil.parseDateYYYYMMdd(endDate));
					pc=cb.and(pc,p7);
				}
				
				if(!StringUtils.isEmpty(create_user)){
					
					p8 =cb.equal(root.get("create_user").as(String.class),create_user);
					pc=cb.and(pc,p8);
				}
				query.where(pc);

				// 添加排序的功能
				//query.orderBy(cb.desc(root.get("id").as(String.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}
	
	public ProductArg saveArg(ProductArg m){
		
		return productArgRepository.save(m);
	}
	
	public void delArg(Long id){
		
		productArgRepository.deleteById(id);
	}
	
	public List<ProductArg> get_product_args(Long productId){
	
		return productArgRepository.findByProductId(productId);
	}
	
	public List<ProductAttr> get_product_attrs(Long productId){
		
		return productAttrRepository.findByProductId(productId);
	}
	
	public List<ProductImage> get_product_images(Long productId){
		
		return productImageRepository.findByProductId(productId);
	}
	
	public List<ProductImage> get_product_images(Long productId,Integer postion){
		
		return productImageRepository.findByProductIdAndPostion(productId,postion);
	}
	
	public Long getNewProductId(){
		
		return productRepository.getNewProductId();
	}
	
	public void delImage(Long id){
	
		productImageRepository.deleteById(id);
	}
	
	public ProductImage saveImage(ProductImage m){
		return productImageRepository.save(m);
	}
	
	public Product getProductById(Long id){
		return productRepository.findById(id).orElse(null);
	}
}
