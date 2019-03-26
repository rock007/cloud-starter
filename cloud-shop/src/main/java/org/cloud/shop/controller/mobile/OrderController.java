package org.cloud.shop.controller.mobile;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cloud.core.model.JsonBody;
import org.cloud.db.shop.entity.*;
import org.cloud.db.shop.enumType.OrderStatusType;
import org.cloud.db.shop.enumType.RoleType;
import org.cloud.db.shop.repository.*;
import org.cloud.db.shop.service.OrderService;
import org.cloud.db.shop.service.ProductService;
import org.cloud.shop.ShopApplication;
import org.cloud.shop.model.SubmitOrderModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private PayRepository payRepository;
	
	//!!@Autowired
	//!!private DxDbBean  dbBean;
	
	//@Autowired
	//private ActLogService actLogService;
	
	//@Autowired
	//private MessageService messageService;
	
	@Autowired
	private GoodsPackageRepository goodsPackageRepository;
	
	@Autowired
	private OrderReqBackRepository orderReqBackRepository;
	
	@ExceptionHandler
	public @ResponseBody
    JsonBody<String> handleException(HttpServletRequest request, Exception ex){
	   
		logger.error("handleException", ex);
		return new JsonBody(-1,ex.getMessage()) ;
	}
	
	@RequestMapping(value="/order.json")
	public @ResponseBody String index(){
		
		return "shop";
	}
	/**
	 * 提交订单
	 */
	@RequestMapping(value="/submit-order.action",method = RequestMethod.POST )
	public @ResponseBody JsonBody<String> submit_Order(@ModelAttribute SubmitOrderModel m){

        JsonBody<String> result;
		Product product= productService.getProductById(m.getProductId());
		
		if(product!=null){
			
			m.setOrderStatus(OrderStatusType.nopay);
			m.setProduct_num(1);
			//m.setUpdate_date(new Date());
			m.setCreate_date(new Date());
			
			if(ShopApplication.getCurUser()!=null)
				m.setCreateUser(ShopApplication.getCurUser().getUsername());
			
			Order_Product oneOP= orderProductRepository.findByProductIdAndProductUpdate(product.getId(), product.getUpdate_date());
						
			if(oneOP==null){
				
				 oneOP=new Order_Product();
				 
				//备份产品信息
				oneOP.setAttr_name(m.getAttr_name());
				oneOP.setAttr_value(m.getAttr_value());
				
				oneOP.setAttr_price_normal(m.getAttr_price_normal());
				oneOP.setAttr_price_now(m.getAttr_price_now());
				
				oneOP.setCate_name(product.getCateName());
				oneOP.setCreate_date(new Date());
				//oneOP.setCreate_user(App.getCurUser().getUsername());
				oneOP.setCreate_user(product.getCreate_user());
				
				oneOP.setDealer_id(product.getDealerId());
				oneOP.setDesc_img(product.getDesc_img());
				oneOP.setHtml_desc(product.getHtml_desc());
				oneOP.setIs_discount(product.getIs_discount());
				oneOP.setKeywords(product.getKeywords());
				oneOP.setPrice_normal(product.getPrice_normal());
				oneOP.setPrice_now(product.getPrice_now());
				oneOP.setProductId(m.getProductId());
				oneOP.setProductUpdate(product.getUpdate_date());
				oneOP.setShotwords(product.getShortwords());
				oneOP.setTitle(product.getTitle());
				
				oneOP=orderProductRepository.save(oneOP);
			}
			
			m.setOrder_product_id(oneOP.getId());
			
			Order oneOrder=new  Order();
			oneOrder.setAddress_full(m.getAddress_full());
			oneOrder.setAddress_phone(m.getAddress_phone());
			oneOrder.setAddress_user(m.getAddress_user());
			oneOrder.setCreate_date(m.getCreate_date());
			oneOrder.setCreateUser(m.getCreateUser());
			oneOrder.setOrder_product_id(m.getOrder_product_id());
			oneOrder.setOrderStatus(m.getOrderStatus());
			oneOrder.setPrice(m.getPrice());
			oneOrder.setProduct_num(m.getProduct_num());
			
			//产品属性
			oneOrder.setAttrs_text(m.getAttr_name());
			
			oneOrder.setRemarks(m.getRemarks());
			//oneOrder.setUpdate_date(m.getUpdate_date());
			
			//!!oneOrder.setOrderCode(dbBean.getTableKey("order"));
			
			oneOrder.setOrderProduct(null);
			oneOrder=orderRepository.save(oneOrder);
			
			//记录日志		
			//!!actLogService.recOrderLog(oneOrder.getId(), "买家提交订单", App.getCurUser().getUsername());
			
			//发送消息
			//!!messageService.SendNotice(App.getCurUser().getUsername(), product.getCreate_user(), "买家提交订单，订单号为"+oneOrder.getOrderCode()+"，请尽快发货！");
			
			if(m.getCartId()!=null){
				
				//删除购物车里生成订单等商品
				cartRepository.deleteById(m.getCartId());
			}
			/***
			if(pay_it_later!=1){
				
				//定义支付
				Pay onePay=new Pay();
				
				onePay.setCreate_date(new Date());
				onePay.setOrderId(oneOrder.getId());
				onePay.setPay_money(m.getPrice());
				onePay.setPay_type(m.getPay_type());
				onePay.setProduct_url(UrlFactory.product_deteail(m.getProductId()));
				
				onePay.setRemarks(m.getAttr_name()+":"+m.getAttr_value());
				
				onePay.setTitle(App.getCurUser().getUsername()+"购买"+product.getTitle());
				//onePay.setTrade_no(trade_no);
				//onePay.setTrade_status(trade_status);
				//onePay.setUpdate_date(update_date);
				
				payRepository.save(onePay);
			}
			***/
			//继续提交
			result=new JsonBody<String>(1,"提交订单成功","ajax/pay.html?orderid="+oneOrder.getId());
		}else{
			result=new JsonBody<String>(-1,"参数错误");
		}		
		return result;
	}
	
	@RequestMapping(value="/orders.json")
	public @ResponseBody JsonBody<Page<Order>> products(@ModelAttribute Order m,
                                                  @RequestParam(value="page",required=true)  int page,
                                                  @RequestParam(value="limit",required=true)  int limit,
                                                  String beginDate, String endDate, Integer status){
		
		//m.setOrderStatus(0);
		
		if(ShopApplication.getCurUser()!=null&&ShopApplication.getCurUserRole()!=RoleType.admin)
			m.setCreateUser(ShopApplication.getCurUser().getUsername());
		
		Page<Order> pageRec= orderService.search(m, page-1,limit,beginDate,endDate,status);
		
		return new JsonBody<Page<Order>>(1,"" ,pageRec);
	}
	
	@RequestMapping(value="/pay-order.action",method = RequestMethod.POST)
	public @ResponseBody JsonBody<String> aliapay_create(@ModelAttribute Pay m ,HttpServletResponse response,
			HttpServletRequest request){

        JsonBody<String>  rsult=new JsonBody<String> ();
		//定义支付
		Pay onePay=payRepository.findByOrderId(m.getOrderId());;
		
		Order mOrder= orderRepository.findById(m.getOrderId()).orElse(null);
		if(onePay==null){
		
			onePay=new Pay();
			
			onePay.setCreate_date(new Date());
			onePay.setOrderId(mOrder.getId());
			onePay.setPay_money(mOrder.getPrice());
			onePay.setPay_type(m.getPay_type());
			//!!!onePay.setProduct_url(UrlFactory.product_deteail(mOrder.getOrderProduct().getProductId()));
			
			onePay.setRemarks(mOrder.getAttrs_text());
			
			onePay.setTitle(ShopApplication.getCurUser().getUsername()+"购买"+mOrder.getOrderProduct().getTitle());
			
		}else {
			
			onePay.setPay_type(m.getPay_type());
		}
		
		//onePay.setTrade_no(trade_no);
		//onePay.setTrade_status(trade_status);
		//onePay.setUpdate_date(update_date);
		
		if(m.getPay_type().equals("carsh")){
			
			//设置付款完成（到付）
			mOrder.setUpdate_date(new Date());
			mOrder.setOrderStatus(OrderStatusType.pay);
			orderRepository.save(mOrder);
			
			//记录日志		
			//!!actLogService.recOrderLog(mOrder.getId(), "买家选择货到付款", App.getCurUser().getUsername());
			
			//发送消息

			//!!messageService.SendNotice(App.getCurUser().getUsername(), mOrder.getOrderProduct().getCreate_user(), "买家选择支付方式，订单号为"+mOrder.getOrderCode()+".");
			
			rsult= new JsonBody<String> (1,"选择支付方式成功","ajax/order-"+m.getOrderId()+".html");
			
		}else{
			
			payRepository.save(onePay);
			
			//记录日志		
			//!!actLogService.recOrderLog(mOrder.getId(), "买家选择支付方式", App.getCurUser().getUsername());

			//!!messageService.SendNotice(App.getCurUser().getUsername(), mOrder.getOrderProduct().getCreate_user(), "买家选择支付方式，订单号为"+mOrder.getOrderCode()+".");
			
			rsult= new JsonBody<String> (1,"选择支付方式成功","ajax/pay-result.html");
		}
		
		return rsult;
	}
	
	@RequestMapping(value="/order-reqback.action",method = RequestMethod.POST)
	public @ResponseBody JsonBody<String>  submit_reqback(@ModelAttribute OrderReqBack m ,
			String ems_name,String ems_no){
	
		if(m.getIs_received()==1){
			
			//保存物流信息
			GoodsPackage onePackage=new GoodsPackage();
			onePackage.setCreate_date(new Date());
			onePackage.setEms_name(ems_name);
			onePackage.setEms_no(ems_no);
			onePackage.setFlag(1);
			onePackage.setOrderId(m.getOrderId());
			onePackage.setStatus(0);
			
			onePackage=goodsPackageRepository.save(onePackage);
			
			m.setEms_id(onePackage.getId());
		}
		m.setCreate_date(new Date());
		m.setGoodsEms(null);
		m.setStatus(0);
		m.setUpdate_date(new Date());
		
		orderReqBackRepository.save(m);
		
		/*******更新订单，消息 begin***********************************************/
		//记录日志		
		//!!actLogService.recOrderLog(m.getOrderId(), "买家申请退款", App.getCurUser().getUsername());
		
		//发送消息
		String productOwner= orderService.findOrderProductCreateUser(m.getOrderId());

		//messageService.SendNotice(App.getCurUser().getUsername(), productOwner, "买家申请退款，请跟进，订单id为"+m.getId()+".");
		
		/*******更新订单，消息 end***********************************************/
		
		return new JsonBody<String> (1,"申请退款操作成功","ajax/order-reqback.html?orderId="+m.getOrderId());
	}
	
	
	/**
	 * 提醒发货(1/24hour)
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value="/send-req-pack.action")
	public @ResponseBody JsonBody<String>  send_reqPack(Long orderId){
		
		String productOwner= orderService.findOrderProductCreateUser(orderId);
        JsonBody<String>  result;
		Long days= 100L;//!!actLogService.findActLastDateCompereNow(orderId);
		
		if(days==0||days>1){
			//记录日志		
			//actLogService.recOrderLog("提醒发货",orderId, "买家提醒发货", App.getCurUser().getUsername());

			//messageService.SendNotice(App.getCurUser().getUsername(), productOwner, "买家提醒发货，订单id为"+orderId+".");
			
			result=new JsonBody<String> (1,"发送提醒发货成功");
		}else{
			
			result=new JsonBody<String> (-1,"提醒发货24小时只能发送一次！");
		}
		
		return result;
	}
}
