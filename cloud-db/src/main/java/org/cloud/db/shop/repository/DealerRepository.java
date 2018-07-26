package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.Dealer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DealerRepository extends CrudRepository<Dealer, Long> {

	/**
	 * 经销商用户获取经销商信息
	 * @param user
	 * @return
	 */
    Dealer findByMainUser(String user);
    
    /***
     * 普通消费者用户获取与之对话的经销商列表
     * @param user1
     * @return
     */
    @Query(value=" SELECT v1.* "+
    		" FROM shop_dealer v1 "+
    		" WHERE main_user IN "+
    		"   ( "+ 
    		"		select dealer_name from shop_msg_group where customer_name=:user1 "+
    		"   )  ",nativeQuery=true)
     public List<Dealer> findMsgDealers(@Param("user1") String user1);
    
}