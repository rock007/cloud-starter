package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.ProductImage;
import org.springframework.data.repository.CrudRepository;

public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {
    
    List<ProductImage> findByProductId(Long productId);
    
    List<ProductImage> findByProductIdAndPostion(Long productId,Integer postion);
}