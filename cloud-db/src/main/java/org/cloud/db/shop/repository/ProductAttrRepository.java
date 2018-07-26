package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.ProductAttr;
import org.springframework.data.repository.CrudRepository;

public interface ProductAttrRepository extends CrudRepository<ProductAttr, Long> {

    List<ProductAttr> findByCreateUser(String user);
    
    List<ProductAttr> findByProductId(Long productId);
}