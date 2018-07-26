package org.cloud.shop.controller.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.cloud.db.shop.entity.*;
import org.cloud.db.shop.repository.*;
import org.cloud.db.shop.service.ProductService;
import org.cloud.shop.ShopApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WebAppController {
	
	private static final Logger logger = LoggerFactory.getLogger(WebAppController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	//!!@Autowired
	//!!private MessageService messageService;
	
	@Autowired
	private DealerRepository dealerRepository;
	
	//!!!@Autowired
	//private AccountRepository accountRepository;
	
	@Autowired
	private GoodsPackageRepository goodsPackageRepository;
	
	@Autowired
	private OrderReqBackRepository orderReqBackRepository;
	
	
	@ExceptionHandler
	public @ResponseBody String handleException(HttpServletRequest request, Exception ex){
	   
		logger.error("出错了", ex);
		return ex.getMessage();
	 }
	
	@RequestMapping(value="/")
	public  String home(){

		return "redirect:index.html";
	}
	
	@RequestMapping(value="/login")
	public String login(){
		
		return "login";
	}
	
	@RequestMapping(value="/index.html")
	public  String index(@CookieValue(value = "cart-cookie-uid", defaultValue = "") String uid,
			HttpServletResponse response,
			HttpSession session){
		
		if(StringUtils.isEmpty(uid)){
			
			//如果为空
			uid=session.getId();
			Cookie cart_cookie = new Cookie("cart-cookie-uid", uid); 
			cart_cookie.setMaxAge(1000*60*60*24*30); 
			cart_cookie.setPath("/");
			response.addCookie(cart_cookie);
		}
		
		//登录成功后
		if( ShopApplication.getCurUser()!=null){
		
			List<Cart> list=cartRepository.findByCartUid(uid);
			
			for(Cart c:list){
				
				if(c.getCreateUser()==null){
					c.setCreateUser(ShopApplication.getCurUser().getUsername());
					cartRepository.save(c);
				}
			}
		}
		return "shop";
	}
	
	@RequestMapping(value="/ajax/dashboard.html")
	public  String dashboard(){
		
		return "shop/dashboard";
	}
	
	@RequestMapping(value="/ajax/address.html")
	public  String address(Map<String, Object> model,String id){
		
		List<Address> list=new ArrayList<Address>();
		Address editAddress=new Address();
		
		if( ShopApplication.getCurUser()!=null){
			
			list=addressRepository.findByCreateUser(ShopApplication.getCurUser().getUsername());
			
		}else{
			return "shop/login";
		}
		
		model.put("list", list);
		
		if(StringUtils.isNotEmpty(id)){
			editAddress= addressRepository.findOne(Long.valueOf(id));
		}
		model.put("edit", editAddress);
		
		return "shop/address";
	}
	@RequestMapping(value="/ajax/category.html")
	public  String category(){
		
		return "shop/category";
	}
	@RequestMapping(value="/ajax/chat.html")
	public  String chat(String seller,Map<String, Object> model){

		if( ShopApplication.getCurUser()==null){
			
			return "shop/login";
		}
		
		if(StringUtils.isNotEmpty(seller)){
		
			//!!!Long gid=messageService.findMsgGroup(seller, App.getCurUser().getUsername());
			
			model.put("gid", 0);//gid
		}
		
		model.put("to_user", seller);
		model.put("from_user", ShopApplication.getCurUser().getUsername());
		return "shop/chat";
	}
	@RequestMapping(value="/ajax/msg.html")
	public  String msg(Map<String, Object> model){
		
		if( ShopApplication.getCurUser()==null){
			
			return "shop/login";
		}
		List<Dealer> list= dealerRepository.findMsgDealers(ShopApplication.getCurUser().getUsername());
		model.put("list", list);
		return "shop/msg";
	}
	
	@RequestMapping(value="/ajax/myboard.html")
	public  String myboard(HttpSession session,Map<String, Object> model){
		
		if( ShopApplication.getCurUser()==null){
			
			return "shop/login";
		}

		//!!!!
		//Account curAccount= accountRepository.findByUsername(ShopApplication.getCurUser().getUsername());
		
		//model.put("cur", curAccount);
		//clear 
		session.removeAttribute(ShopApplication.CON_SESSION_PAY_ADDRESS);
		session.removeAttribute(ShopApplication.CON_SESSION_PAY_CARTS);
		
		return "shop/myboard";
	}
	
	@RequestMapping(value="/ajax/order-{id}.html")
	public  String order(@PathVariable Long id,Map<String, Object> model){
		
		if( ShopApplication.getCurUser()==null){
			
			return "shop/login";
		}
		Order one= orderRepository.findOne(id);
		GoodsPackage goodsPackage;
		
		if(one!=null){
			
			goodsPackage= goodsPackageRepository.findByOrderId(one.getId());
			
			if(goodsPackage!=null)
				model.put("goodsPackage", goodsPackage);
		}
		
		//经销商
		Dealer oneDealer= dealerRepository.findByMainUser(one.getOrderProduct().getCreate_user());
		if(oneDealer!=null){
		
			model.put("dealer", oneDealer);
		}
		
		OrderReqBack reqBack=orderReqBackRepository.findByOrderId(one.getId());
		
		model.put("reqBack", reqBack);
		
		model.put("one", one);
				
		return "shop/order";
	}
	@RequestMapping(value="/ajax/orders.html")
	public  String orders(Integer status,Map<String, Object> model){
		
		if( ShopApplication.getCurUser()==null){
			
			return "shop/login";
		}

		if(status==null){
			status=0;
		}
		
		//List<Order> list= orderRepository.findByCreateUserAndOrderStatus(App.getCurUser().getUsername(), status);
		
		//model.put("list", list);
		//model.put("status", status);
		return "shop/orders";
	}
	@RequestMapping(value="/ajax/pay-result.html")
	public  String pay_result(){
		
		return "shop/pay-result";
	}

	@RequestMapping(value="/ajax/pay.html")
	public  String pay(Long orderid, 
			HttpServletRequest request,
			HttpSession session,Map<String, Object> model){
		
		try{
			
			if( ShopApplication.getCurUser()==null){
				
				return "shop/login";
			}
			
			Order one= orderRepository.findOne(orderid);
			
			model.put("order", one);
			
		}catch(Exception ex){
		
			logger.error("初始化订单", ex);
		}
		return "shop/pay";
	}
	
	@RequestMapping(value="/ajax/product-{id}.html")
	public  String product(@PathVariable Long id,Map<String, Object> model){
		

		Product m= productService.getProductById(id);
		model.put("product", m);
		model.put("time", new Date());

		if( ShopApplication.getCurUser()!=null){
			
			model.put("curUser", ShopApplication.getCurUser());
		}
		
		//经销商
		Dealer oneDealer=dealerRepository.findOne(m.getDealerId());
		model.put("dealer", oneDealer);
		
		return "shop/product";
	}
	@RequestMapping(value="/ajax/products.html")
	public  String products(Map<String, Object> model, String k,String c){
		
		model.put("keyword", k);
		model.put("cate", c);
		return "shop/products";
	}
	@RequestMapping(value="/ajax/search.html")
	public  String search(){
		
		return "shop/search";
	}
	@RequestMapping(value="/ajax/shop-cart.html")
	public  String shop_cart(@CookieValue(value = "cart-cookie-uid", defaultValue = "") String uid,
			Map<String, Object> model){
		
		List<Cart> list=new ArrayList<Cart>();
		
		if( ShopApplication.getCurUser()!=null){
			
			list=cartRepository.findByCreateUser(ShopApplication.getCurUser().getUsername());
		}else{
			list=cartRepository.findByCartUid(uid);
		}
		model.put("list", list);
		return "shop/shop-cart";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ajax/submit-order.html")
	public  String submit_order(HttpSession session,Map<String, Object> model){
		
		if( ShopApplication.getCurUser()==null){
			
			return "shop/login";
		}

		Object cartObj=session.getAttribute(ShopApplication.CON_SESSION_PAY_CARTS);
		List<Cart> carts=new ArrayList<Cart>();
		
		try{
			if(cartObj!=null){
				
				carts= (ArrayList<Cart>) cartObj;
				
				if(carts.size()>0){

					Cart one=carts.get(0);
					
					Product payProduct=productService.getProductById(one.getProductId());
					one.setProduct(payProduct);
					
					model.put("oneCart", one);
					
					Long addressId=(Long)session.getAttribute(ShopApplication.CON_SESSION_PAY_ADDRESS);
					
					Address oneAddress=addressRepository.findOne(addressId);
					
					if(oneAddress!=null){
						
						String full_address=oneAddress.getProvince()+" "+oneAddress.getCity()+" "+oneAddress.getArea()+" "+
								oneAddress.getRec_address();
						model.put("address_user", oneAddress.getUser_name());
						model.put("address_phone", oneAddress.getUser_phone());
						model.put("address_full", full_address);					
					}

					//成功后，删除
					//!!!carts.remove(one);
					session.setAttribute(ShopApplication.CON_SESSION_PAY_CARTS, carts);
					
				}else{
				//!!!	session.removeAttribute(App.CON_SESSION_PAY_CARTS);
				}
			}
			
		}catch(Exception ex){
		
			logger.error("初始化订单", ex);
		}
		return "shop/submit-order";
	}
	
	/**
	 * 申请退款
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ajax/order-reqback.html")
	public  String order_reqback(Map<String, Object> model,Long orderId){
		
		if( ShopApplication.getCurUser()==null){
			
			return "shop/login";
		}
		
		OrderReqBack reqBack=orderReqBackRepository.findByOrderId(orderId);
		
		model.put("orderId", orderId);
		model.put("reqBack", reqBack);
		
		return "shop/order-reqback";
		
	}
	
	@RequestMapping(value="/ajax/dealer-{id}.html")
	public  String dealer(@PathVariable Long id,Map<String, Object> model){
	
		//经销商
		Dealer oneDealer=dealerRepository.findOne(id);
		model.put("dealer", oneDealer);
		return "shop/dealer-shop";
	}
	
}
