package org.cloud.shop.controller.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.cloud.core.model.JsonBody;
import org.cloud.db.shop.entity.Cart;
import org.cloud.db.shop.repository.CartRepository;
import org.cloud.shop.ShopApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartRepository cartRepository;

	@RequestMapping(value="/add-cart.action",method = RequestMethod.POST )
	public @ResponseBody
    JsonBody<String> add_Cart(@CookieValue(value = "cart-cookie-uid", defaultValue = "") String uid,
                              @ModelAttribute Cart m, HttpSession session){

        JsonBody<String> result;
		if(m.getProductId()!=null){
			
			m.setCartUid(uid);
			m.setCreate_date(new Date());
			
			if(ShopApplication.getCurUser()!=null)
				m.setCreateUser(ShopApplication.getCurUser().getUsername());
			
			m.setProduct_num(1);
			m.setStatus(0);
			//m.setPrice(price);
			//m.setProductId(productId);
			
			//判断重复，暂时不考虑,ps：不影响
			m.setProduct(null);
			cartRepository.save(m);
			
			result=new JsonBody(1,"添加购物车成功","ajax/shop-cart.html");
		}else{
			result=new JsonBody(-1,"参数错误");
		}		
		return result;
	}
	
	@RequestMapping(value="/rm-cart.action",method = RequestMethod.POST )
	public @ResponseBody JsonBody<String> rm_Cart(@CookieValue(value = "cart-cookie-uid", defaultValue = "") String uid,
			@ModelAttribute Cart m,HttpSession session,String cart_ids){

        JsonBody<String> result;
		
		String carts[]=cart_ids.split(",");
		
		for(String c:carts){
			
			if(StringUtils.isNotEmpty(c)){
				cartRepository.delete(Long.valueOf(c.trim()));
			}
		}
		result=new JsonBody<String>(1,"购物车删除产品成功","ajax/shop-cart.html");
				
		return result;
	}
	

	@RequestMapping(value="/pay-carts.action",method = RequestMethod.POST )
	public @ResponseBody JsonBody<String> pay_carts(String cart_ids,
			HttpSession session){

        JsonBody<String> result;
		
		String carts[]=cart_ids.split(",");
		List<Cart> pay_carts=new ArrayList<Cart>();
		
		for(String c:carts){
			
			if(StringUtils.isNotEmpty(c)){
				Cart one= cartRepository.findOne(Long.valueOf(c));
				
				if(one!=null)
					pay_carts.add(one);
			}
		}
		session.setAttribute(ShopApplication.CON_SESSION_PAY_CARTS, pay_carts);
		
		result=new JsonBody<String>(1,"购物车产品准备支付","ajax/address.html");
				
		return result;
	}
	
	@RequestMapping(value="/pay-now.action",method = RequestMethod.POST )
	public @ResponseBody JsonBody<String> pay_now(@ModelAttribute Cart m,HttpSession session){
		
		List<Cart> pay_carts=new ArrayList<Cart>();
        JsonBody<String> result;
		
		if(ShopApplication.getCurUser()==null){
			
			return new JsonBody<String>(-1,"请登录系统","ajax/login.html");
		}

		if(m.getProductId()!=null){
			
			m.setCreateUser(ShopApplication.getCurUser().getUsername());
			pay_carts.add(m);
			
			result=new JsonBody<String>(1,"产品立即支付","ajax/address.html");
		}else{
			result=new JsonBody<String>(-1,"参数错误");
		}
		session.setAttribute(ShopApplication.CON_SESSION_PAY_CARTS, pay_carts);
		
		return result;
	}
	
	@RequestMapping(value="/pay-address.action",method = RequestMethod.POST )
	public @ResponseBody JsonBody<String> pay_address( Long address_id,HttpSession session){

        JsonBody<String> result;
		
		if(address_id!=null){

			session.setAttribute(ShopApplication.CON_SESSION_PAY_ADDRESS, address_id);
			
			result=new JsonBody<String>(1,"确认订单","ajax/submit-order.html");
		}else{
			result=new JsonBody<String>(-1,"参数错误");
		}
		
		return result;
	}
}
