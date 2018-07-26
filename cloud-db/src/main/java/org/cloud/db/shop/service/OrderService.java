package org.cloud.db.shop.service;

import java.util.List;

import org.cloud.db.shop.entity.Dealer;
import org.cloud.db.shop.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

	public Page<Order> search(final Order m, int page, int pageSize, String beginDate, String endDate, Integer status);
	
	public Page<Order> search4Dealer(final Order m,int page,int pageSize,String beginDate,String endDate,Integer status, String productUser);

	
	public String submitDealer(Dealer m);
	
	public Dealer loadDealerInfo(String userName);

	public Dealer findOrderProductOwer(Long id);
	
	public String findOrderProductCreateUser(Long id);

	public  Order findByOrderCode(String orderCode);
}
