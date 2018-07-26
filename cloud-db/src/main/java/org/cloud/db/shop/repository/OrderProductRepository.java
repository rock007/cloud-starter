package org.cloud.db.shop.repository;

import java.util.Date;
import java.util.List;

import org.cloud.db.shop.entity.Order_Product;
import org.springframework.data.repository.CrudRepository;


public interface OrderProductRepository extends CrudRepository<Order_Product, Long> {

    Order_Product findByProductIdAndProductUpdate(Long productId,Date productUpdate);
}