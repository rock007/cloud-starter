package org.cloud.rest.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cloud.core.model.JsonBody;
import org.cloud.db.cms.entity.Article;
import org.cloud.db.cms.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/blog")
public class BlogController extends SuperController{

	private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
	 
	@Autowired
	ArticleService articleService;
	
	@ExceptionHandler
	public @ResponseBody JsonBody<String> handleException(HttpServletRequest request, Exception ex){
	   
		logger.error("handleException", ex);
		return new JsonBody<String>(-1,ex.getMessage()) ;
	}
	
	@RequestMapping("/{userName}/articles")
	public @ResponseBody JsonBody<Page<Article>> index(Map<String, Object> model,@PathVariable String userName,
			@RequestParam(value="page",required=false,defaultValue="0")  int page,
			@RequestParam(value="limit",required=false,defaultValue="20")  int limit) {
		
		Article m=new Article();
		Page<Article> articlePage= articleService.searchBy(m, page, limit);
		
		return new JsonBody<Page<Article>>(1,"ok",articlePage) ;
	}
	
	@RequestMapping(value="/submit-blog-edit.action",method = RequestMethod.POST)
	public @ResponseBody JsonBody<String> submit_edit(@ModelAttribute Article m) {
		
		JsonBody<String> result=null;
		
		try{
			
			if(m.getId()!=null){

				Article existOne= articleService.findById(m.getId());
				if(existOne!=null){
					
					existOne.setUpdate_date(new Date());
					existOne.setTitle(m.getTitle());
					existOne.setContent_html(m.getContent_html());
					
					articleService.save(existOne);
					
					result=new JsonBody<String>(1,"保存成功");
					
				}else{
					
					result=new JsonBody<String>(-1,"保存失败，该文章没有找到！");
				}
			}else{
				
				m.setCreate_date(new Date());
				m.setUpdate_date(new Date());
				m.setStatus(0);
				m.setView_num(0);
				//m.setCreateUser(App.getCurUser().getUsername());
				
				articleService.save(m);
				
				result=new JsonBody<String>(1,"保存成功");
			}
			
		}catch(Exception ex){
			logger.error("ex", ex);
			
			result=new JsonBody<String>(-1,"系统故障，请稍后重试！");
		}
		return result;
	}
	
	
}
