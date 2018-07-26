package org.cloud.shop.controller.mobile;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cloud.core.model.JsonBody;
import org.cloud.db.shop.entity.Product;
import org.cloud.db.shop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@ExceptionHandler
	public @ResponseBody String handleException(HttpServletRequest request, Exception ex){
	   
		return ex.getMessage();
	 }
	
	@RequestMapping(value="/search.json")
	public @ResponseBody JsonBody<Page<Product>> search_products( @ModelAttribute Product m,
			@RequestParam(value="page",required=true)  int page,
			@RequestParam(value="limit",required=true)  int limit,
			String beginDate,String endDate){
		
		m.setStatus(0);
		
		Page<Product> pageRec= productService.search(m, page-1,limit,beginDate,endDate);
		
		return new JsonBody<Page<Product>>(1,"success" ,pageRec);
	}
	
	@RequestMapping(value="/change-product-status.action")
	public @ResponseBody
    JsonBody<String> set_product_status(Long id, Integer status){
		
		Product pro= productService.getProductById(id);
		
		if(pro!=null){
			
			pro.setStatus(status);
			pro.setUpdate_date(new Date());
			productService.save(pro);
		}
		
		return new JsonBody<String>(1,"操作成功！");
	}
	
}
