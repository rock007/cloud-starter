package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.GoodsPackage;
import org.springframework.data.repository.CrudRepository;

public interface GoodsPackageRepository extends CrudRepository<GoodsPackage, Long> {

    GoodsPackage findByOrderId(Long orderId);
    
    List<GoodsPackage> findByStatus(Integer status);
}