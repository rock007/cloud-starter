package org.cloud.db.shop.service;

import java.util.List;

import org.cloud.db.shop.entity.Product;
import org.cloud.db.shop.entity.ProductArg;
import org.cloud.db.shop.entity.ProductAttr;
import org.cloud.db.shop.entity.ProductImage;
import org.springframework.data.domain.Page;

public interface ProductService {

	public Product save(Product m);
	
	public ProductAttr saveAttr(ProductAttr m);

	public void delAttr(Long id);
	
	public Page<Product> search(final Product m,int page,int pageSize,String beginDate,String endDate);

	public ProductArg saveArg(ProductArg m);
	
	public void delArg(Long id);
	
	public List<ProductArg> get_product_args(Long productId);
	
	public List<ProductAttr> get_product_attrs(Long productId);
	
	public List<ProductImage> get_product_images(Long productId);
	
	public List<ProductImage> get_product_images(Long productId,Integer postion);
	
	public Long getNewProductId();
	
	public void delImage(Long id);
	
	public ProductImage saveImage(ProductImage m);
	
	public Product getProductById(Long id);
	
}
