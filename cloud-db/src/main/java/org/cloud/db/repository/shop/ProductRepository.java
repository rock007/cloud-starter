package org.cloud.db.repository.shop;

import org.cloud.db.entity.shop.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
	
}