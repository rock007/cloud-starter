/**
*@Author: sam
*@Date: 2017年11月13日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.cms.controller.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloud.core.base.JsonBaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.cms.entity.Article;
import org.cloud.db.cms.entity.ArticleImage;
import org.cloud.db.cms.service.ArticleService;
import org.cloud.db.sys.entity.UploadFile;
import org.cloud.db.sys.service.UploadFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@CrossOrigin( maxAge = 3600)
@Controller
@RequestMapping(value="/api")
public class ArticleRestController  extends JsonBaseController{

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UploadFileService uploadFileService;
	

	/**
	 * 获取文章列表（带搜索）
	 * @param m
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/search-article.action")
	public @ResponseBody  JsonBody<Page<Article>> search_article(@ModelAttribute Article m
			,@RequestParam(value="page",required=false,defaultValue="0")  int pageIndex
			,@RequestParam(value="pageSize",required=false,defaultValue="10")  int pageSize) {
		
		Page<Article> page=articleService.searchBy(m, pageIndex, pageSize);
		
		for(Article a:page.getContent()){

			List<ArticleImage> aImgList=articleService.get_images(a.getId());
  			
			a.setImages(aImgList);
		}
		
		return new JsonBody<>(1,"获取数据成功",page);
	} 
	
	@GetMapping("/get-article.action")
	public @ResponseBody  JsonBody<Article> get_article(Long aid) {
		
		if(aid==null||aid==0){
			
			return new JsonBody<>(-1,"aid 不能为空");
		}
		
		Article existOne= articleService.findById(aid);
		if(existOne==null||existOne.getStatus()==-1){
			
			return new JsonBody<>(-2,"抱歉，文章不存在或者已经删除");
		}
		
		return new JsonBody<>(1,"获取数据成功",existOne);
	}
	
	 @RequestMapping(value="/article-new.json",method=RequestMethod.POST)
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
			 article.setCreateUser(getLoginUid());
			 
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
	
	@PostMapping("/article-edit.json")
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
			 article.setCreateUser(getLoginUid());
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

	 @GetMapping(value="/article-rm.json")
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

