package org.cloud.db.shop.repository;

import org.cloud.db.shop.entity.PayRefund;
import org.springframework.data.repository.CrudRepository;

public interface PayRefundRepository extends CrudRepository<PayRefund, String> {

	PayRefund findByOrderId(Long orderId);
	
}