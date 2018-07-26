package org.cloud.db.shop.repository;

import org.cloud.db.shop.entity.Pay;
import org.springframework.data.repository.CrudRepository;

public interface PayRepository extends CrudRepository<Pay, Long> {

    Pay findByOrderId(Long orderId);
    
}