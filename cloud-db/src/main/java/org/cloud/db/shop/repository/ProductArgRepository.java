package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.ProductArg;
import org.springframework.data.repository.CrudRepository;


public interface ProductArgRepository extends CrudRepository<ProductArg, Long> {

    List<ProductArg> findByProductId(Long productId);
}