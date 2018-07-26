package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long> ,JpaSpecificationExecutor<Product> {

	List<Product> findByCateName(String cate);
    
    List<Product> findByDealerId(Long dealerId);
    
    @Query(value=" select shop_product_add_seq.nextval from dual " ,nativeQuery=true)
    public Long getNewProductId();
}