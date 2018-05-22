package org.cloud.db.shop.repository;

import org.cloud.db.shop.entity.ProductImage;
import org.springframework.data.repository.CrudRepository;

public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {
	
}