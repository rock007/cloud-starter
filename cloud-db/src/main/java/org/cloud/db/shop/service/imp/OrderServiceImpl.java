package org.cloud.db.shop.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.cloud.core.utils.DateUtil;
import org.cloud.db.shop.entity.Dealer;
import org.cloud.db.shop.entity.Order;
import org.cloud.db.shop.enumType.OrderStatusType;
import org.cloud.db.shop.repository.DealerRepository;
import org.cloud.db.shop.repository.OrderRepository;
import org.cloud.db.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("orderService")
public class OrderServiceImpl  implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private DealerRepository dealerRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Dealer loadDealerInfo(String userName) {
		Dealer dealer = dealerRepository.findByMainUser(userName);
		return dealer;
	}
	
	@Override
	public String submitDealer(Dealer m) {
		String result = "";
		Dealer dealer = null;
		if(m.getId() == null) {
			//新增一条记录 保存
			dealer = new Dealer();
			dealer.setName(m.getName());
			dealer.setAddress(m.getAddress());
			dealer.setArea(m.getArea());
			dealer.setCity(m.getCity());
			dealer.setLink_man(m.getLink_man());
			dealer.setLink_mobile(m.getLink_mobile());
			dealer.setMainUser(m.getMainUser());
			dealer.setStatus(m.getStatus());
			dealer.setCreate_date(new Date());
			dealerRepository.save(dealer);
			System.out.println("Dealer新增一条记录成功！");
			result = "save";
		}else {
			//Dealer dealer = em.find(Dealer.class, m.getId());
			dealer = dealerRepository.findById(m.getId()).orElseThrow();
			dealer.setName(m.getName());
			dealer.setAddress(m.getAddress());
			dealer.setArea(m.getArea());
			dealer.setCity(m.getCity());
			dealer.setLink_man(m.getLink_man());
			dealer.setLink_mobile(m.getLink_mobile());
			dealer.setMainUser(m.getMainUser());
			dealer.setStatus(m.getStatus());
			dealerRepository.save(dealer);
			System.out.println("Dealer更新一条记录成功！");
			result = "update";
		}

		return result;
	}

	
	@Override
	public Page<Order> search(final Order m, int page, int pageSize,
                              final	String beginDate, final String endDate, final Integer status) {
		
		return orderRepository.findAll(new Specification<Order>() {

			public Predicate toPredicate(Root<Order> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				String title="";
				
				//OrderStatusType status=null ; //状态
				
				String create_user=m.getCreateUser();
				
				Predicate p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,pc;
				
				if (!StringUtils.isEmpty(title)) {
					p1 = cb.like(root.get("title").as(String.class), "%"
							+ title.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "1");
					pc=cb.and(p1);				
				}
				
				if(status == 0){
					//未完成
					p2 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.nopay);
					p3 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.pay);
					p4 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.packonline);
					p5 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.packarrive);
					
					p9 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.reqback);
					p10 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.confirmdeal);
					
					pc=cb.or(p2,p3,p4,p5,p9,p10);
					
				}else if(status == 9){
					//交易完成
					p2 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.cancel);
					p3 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.backdone);
					p4 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.backcancel);
					p5 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.dealdone);
					
					pc=cb.or(p2,p3,p4,p5);
					
				}else if(status == 100){
					//全部
				}
				
				if(!StringUtils.isEmpty(beginDate)&&DateUtil.parseDateYYYYMMdd(beginDate)!=null){
					
					p6 =cb.greaterThan(root.get("create_date").as(Date.class),DateUtil.parseDateYYYYMMdd(beginDate));
					pc=cb.and(pc,p6);
				}
				
				if(!StringUtils.isEmpty(endDate)&&DateUtil.parseDateYYYYMMdd(endDate)!=null){
					
					p7 =cb.lessThanOrEqualTo(root.get("create_date").as(Date.class),DateUtil.parseDateYYYYMMdd(endDate));
					pc=cb.and(pc,p7);
				}
				
				if(!StringUtils.isEmpty(create_user)){
					
					p8 =cb.equal(root.get("createUser").as(String.class),create_user);
					pc=cb.and(pc,p8);
				}
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.desc(root.get("create_date").as(Date.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}
	
	public Page<Order> search4Dealer(final Order m,int page, int pageSize,
			final  String beginDate,final  String endDate,final Integer status,
			final String productUser){
		
		return orderRepository.findAll(new Specification<Order>() {

			public Predicate toPredicate(Root<Order> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				String title="";
				
				//String create_user=m.getCreateUser();
				
				Predicate p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,pc;
				
				if (!StringUtils.isEmpty(title)) {
					p1 = cb.like(root.get("title").as(String.class), "%"
							+ title.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "1");
					pc=cb.and(p1);				
				}
				
				if(status == 0){
					//未完成
					p2 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.nopay);
					p3 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.pay);
					p4 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.packonline);
					p5 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.packarrive);
					
					p9 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.reqback);
					p10 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.confirmdeal);
					
					pc=cb.or(p2,p3,p4,p5,p9,p10);
					
				}else if(status == 9){
					//交易完成
					p2 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.cancel);
					p3 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.backdone);
					p4 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.backcancel);
					p5 =cb.equal(root.get("orderStatus").as(OrderStatusType.class),OrderStatusType.dealdone);
					
					pc=cb.or(p2,p3,p4,p5);
					
				}else if(status == 100){
					//全部
				}
				
				if(!StringUtils.isEmpty(beginDate)&&DateUtil.parseDateYYYYMMdd(beginDate)!=null){
					
					p6 =cb.greaterThan(root.get("create_date").as(Date.class),DateUtil.parseDateYYYYMMdd(beginDate));
					pc=cb.and(pc,p6);
				}
				
				if(!StringUtils.isEmpty(endDate)&&DateUtil.parseDateYYYYMMdd(endDate)!=null){
					
					p7 =cb.lessThanOrEqualTo(root.get("create_date").as(Date.class),DateUtil.parseDateYYYYMMdd(endDate));
					pc=cb.and(pc,p7);
				}
				
				if(!StringUtils.isEmpty(productUser)){
					
					p8 =cb.equal(root.get("orderProduct").get("create_user").as(String.class),productUser);
					pc=cb.and(pc,p8);
				}
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.desc(root.get("create_date").as(Date.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}
	
	@Override
	public Dealer findOrderProductOwer(Long id){
		
		Order oneOrder= orderRepository.findById(id).orElse(null);
		Dealer oneDealer = null;
		if(oneOrder!=null){
			
			Long delaerId=  oneOrder.getOrderProduct().getDealer_id();
			
			oneDealer=dealerRepository.findById(delaerId).orElse(null);
		}
		
		return oneDealer;
	}
	
	@Override
	public String findOrderProductCreateUser(Long id){
	
		return orderRepository.findProductOwner(id);
	}
	@Override
	public  Order findByOrderCode(String orderCode){
		return orderRepository.findByOrderCode(orderCode);
	}
}
