package org.cloud.db.repository.shop;

import org.cloud.db.entity.shop.ProductImage;
import org.springframework.data.repository.CrudRepository;

public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {
	
}