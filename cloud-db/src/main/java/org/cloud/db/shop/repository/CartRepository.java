package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.Cart;
import org.springframework.data.repository.CrudRepository;


public interface CartRepository extends CrudRepository<Cart, Long> {

    List<Cart> findByProductId(Long productId);
    
    List<Cart> findByCreateUser(String user);
    
    List<Cart> findByCartUid(String sessionId);
}