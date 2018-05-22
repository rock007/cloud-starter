package org.cloud.db.shop.repository;

import org.cloud.db.shop.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
	
}