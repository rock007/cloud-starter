package org.cloud.shop.controller.mobile;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.cloud.core.model.JsonBody;
import org.cloud.db.shop.entity.Address;
import org.cloud.db.shop.repository.AddressRepository;
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
public class AddressController {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private AddressRepository addressRepository;

	@RequestMapping(value="/add-address.action",method = RequestMethod.POST )
	public @ResponseBody
    JsonBody<String> add_Address(HttpSession session, @ModelAttribute Address m, String act_sendto){

        JsonBody<String> result;
		Address saveAddress=new Address();

		if(m.getId()==null){
			//添加
			m.setStatus(0);
			m.setCreate_date(new Date());
			
			if(ShopApplication.getCurUser()!=null)
				m.setCreateUser(ShopApplication.getCurUser().getUsername());
			
			saveAddress=addressRepository.save(m);
			
		}else{
			Address oldOne=addressRepository.findOne(m.getId());
			
			oldOne.setRec_address(m.getRec_address());
			oldOne.setArea(m.getArea());
			oldOne.setCity(m.getCity());
			oldOne.setProvince(m.getProvince());
			oldOne.setUser_name(m.getUser_name());
			oldOne.setUser_phone(m.getUser_phone());
			
			saveAddress=addressRepository.save(oldOne);
		}
		

		if(act_sendto!=null&&"1".equals(act_sendto)){
			//直接发送到此地址
			session.setAttribute(ShopApplication.CON_SESSION_PAY_ADDRESS, saveAddress.getId());
			
			return new JsonBody<String>(1,"送货至此地址","ajax/pay.html");
		}
		
		result=new JsonBody<String>(1,"添加送货地址成功","ajax/address.html");
		
		return result;
	}
	
	@RequestMapping(value="/rm-address.action")
	public @ResponseBody JsonBody<String> rm_Address(HttpSession session,String ids){

        JsonBody<String> result;
		
		String carts[]=ids.split(",");
		
		for(String c:carts){
			
			if(StringUtils.isNotEmpty(c)){
				addressRepository.delete(Long.valueOf(c.trim()));
			}
		}
		result=new JsonBody<String>(1,"删除送货地址成功","ajax/address.html");
				
		return result;
	}
	
	@RequestMapping(value="/set-address.action" )
	public @ResponseBody JsonBody<String> setDefault_Address(String id){

        JsonBody<String> result;
		
		List<Address> list= addressRepository.findByCreateUser(ShopApplication.getCurUser().getUsername());
		
		for(Address c:list){
			
			if(c.getId()==Long.parseLong(id)){
				
				c.setIs_default(1);
				
			}else{
				c.setIs_default(0);
			}
			
			addressRepository.save(c);
		}
		result=new JsonBody<String>(1,"设置默认送货地址成功","ajax/address.html");
				
		return result;
	}
}
