/**
*@Author: sam
*@Date: 2017年11月13日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.backend.controller.rest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloud.core.model.JsonBody;
import org.cloud.db.cms.entity.Article;
import org.cloud.db.cms.entity.ArticleImage;
import org.cloud.db.cms.service.ArticleService;
import org.cloud.db.sys.entity.UploadFile;
import org.cloud.db.sys.service.UploadFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@CrossOrigin( maxAge = 3600)
@Controller
@RequestMapping(value="/rest")
public class ArticleRestController  extends JsonBaseController{

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	 @RequestMapping(value="/article-new.action",method=RequestMethod.POST)
	 public @ResponseBody  JsonBody<String> post_article_new(Model model,  Article article,
			 HttpServletResponse response, HttpServletRequest request){
		
		 	String pics=request.getParameter("pics");
		 	
		 	String[] picArray= pics.split(",");
		 
			if( StringUtils.isEmptyOrWhitespace(article.getContent_html())){
				
				return new JsonBody<>(-1,"内容 不能为空");
			}
			if( StringUtils.isEmptyOrWhitespace(article.getShort_content())){
				
				return new JsonBody<>(-1,"简述不能为空");
			}
			if( article.getCateId()==null){
				
				return new JsonBody<>(-1,"类别不能为空");
			}
			 
			 //添加
			 article.setStatus(0);
			 article.setCreate_date(new Date());
			 article.setCreateUser(getCurUserId());
			 
			 article=articleService.save(article);
			 
			 //更新图片
			 for(String m:picArray){
				 
				 if(StringUtils.isEmptyOrWhitespace(m)) continue;
				 
				 ArticleImage one=new ArticleImage();
				 
				 one.setArticleId(article.getId());
				 one.setCreate_date(new Date());
				 
				 UploadFile uploadfile=uploadFileService.findById(m);
				 if(uploadfile!=null){
					 
					 one.setExt(uploadfile.getFile_ext());
					 one.setPath(uploadfile.getPath());
					 one.setFile_size(uploadfile.getFile_size());
					 
					 articleService.save_articleImage(one);
					 	 
				 }
			 }
			
			return new JsonBody<>(1,"操作成功");
	}
	
	@PostMapping("/article-edit.action")
	public  @ResponseBody  JsonBody<String> post_article_edit(Model model, Article article ,
			String pics,String rm_pic){
		
		if( StringUtils.isEmptyOrWhitespace(article.getContent_html())){
			
			return new JsonBody<>(-1,"内容 不能为空");
		}
		if( StringUtils.isEmptyOrWhitespace(article.getShort_content())){
			
			return new JsonBody<>(-1,"简述不能为空");
		}
		if( article.getCateId()==null){
			
			return new JsonBody<>(-1,"类别不能为空");
		}
		 
		 if(article.getId()!=null&&article.getId()>0){
			 
			 Article existOne=articleService.findById(article.getId());
			 
			 if(existOne==null){
				 
				 return new JsonBody<>(-1,"id不能为空");
			 }
			 //排除非更新字段
			 BeanUtils.copyProperties(existOne,article,new String[] {"id","title","cateId","keywords","short_content","content_html","source","source_url","status"});
			 //编辑
			 article.setUpdate_date(new Date());
			 
		 }else{
			 //添加
			 article.setStatus(0);
			 article.setCreate_date(new Date());
			 article.setCreateUser(getCurUserId());
		 }
		 
		 articleService.save(article);
		 
		 //save pic
		 if(!StringUtils.isEmptyOrWhitespace(pics)){
			 
			 ArticleImage one=new ArticleImage();
			 
			 one.setArticleId(article.getId());
			 //one.setUpload_file_id(pics);
			 one.setCreate_date(new Date());
			 
			 UploadFile uploadfile=uploadFileService.findById(pics);
			 if(uploadfile!=null){
				 
				 one.setExt(uploadfile.getFile_ext());
				 one.setPath(uploadfile.getPath());
				 one.setFile_size(uploadfile.getFile_size());
				 
				 articleService.save_articleImage(one);
				 	 
			 }
		 }
		 
		 //rm pic
		 if(!StringUtils.isEmptyOrWhitespace(rm_pic)){
			
			 articleService.del_artileImage(Long.parseLong(rm_pic));
			 
		 }
		 
		return new JsonBody<>(1,"操作成功");
	}

	 @RequestMapping(value="/article-rm.action")
	 public @ResponseBody  JsonBody<String>  rm_article(final String ids,Integer carType) {
		
		 if(StringUtils.isEmpty(ids)){
			 
				return new JsonBody<>(-1,"ids不能为空");
		 }
		 
		 String id_arr[]=ids.split(",");
		 
		 for(String mm:id_arr){
			 
			 if(StringUtils.isEmpty(mm)) continue;
			 
			 try{
				
				 Long id=Long.parseLong(mm);
				 List<ArticleImage> pics=articleService.get_images(id);
					
				 for(ArticleImage img:pics){
					 
					 articleService.del_artileImage(img.getId());
				 }
				 
				articleService.delete(id);
				
			 }catch(Exception ex){
				 logger.error("rm_article:", ex);
			 }
			 
		 }
		return new JsonBody<>(1,"操作成功");
	 }
}

