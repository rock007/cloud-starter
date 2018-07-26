package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.OrderReqBack;
import org.springframework.data.repository.CrudRepository;


public interface OrderReqBackRepository extends CrudRepository<OrderReqBack, Long> {

	OrderReqBack findByOrderId(Long orderId);
    
    List<OrderReqBack> findByStatus(Integer status);
}