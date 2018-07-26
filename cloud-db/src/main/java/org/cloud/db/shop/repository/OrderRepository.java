package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends CrudRepository<Order, Long>,JpaSpecificationExecutor<Order>  {

    List<Order> findByCreateUser(String user);
    
    List<Order> findByCreateUserAndOrderStatus(String user,Integer status);
    
    @Query(value=" select b.create_user from shop_orders a inner join  shop_order_product b on a.order_product_id = b.id where a.id= ? " ,nativeQuery=true)
    String findProductOwner(Long id);
    
    Order findByOrderCode(String orderCode);
}