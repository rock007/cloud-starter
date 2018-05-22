/**
*@Project: 
*@Author: sam
*@Date: 2016年9月29日
*@Copyright: 2016  All rights reserved.
*/
package org.cloud.backend.controller.rest;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.cloud.core.model.JsonBody;
import org.cloud.db.cms.entity.Article;
import org.cloud.db.cms.entity.ArticleImage;
import org.cloud.db.cms.entity.Comment;
import org.cloud.db.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

/**
 * 文章
 * @author sam
 *
 */
@CrossOrigin( maxAge = 3600)
@Controller
@RequestMapping(value="/rest")
public class CmsRestController extends JsonBaseController{

	@Autowired
	private ArticleService articleService;
	
	/**
	 * 获取文章列表（带搜索）
	 * @param m
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/search-article.action")
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
	
	@RequestMapping("/get-article.action")
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
	
	/**
	 * 获取评论
	 * @param ref_type
	 * @param ref_id
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/get-comments.action")
	public @ResponseBody  JsonBody<Page<Comment>> get_comments(@RequestParam(value="ref_type",required=false,defaultValue="0") Long ref_type,Long ref_id
			,@RequestParam(value="page",required=false,defaultValue="0")  int pageIndex,@RequestParam(value="pageSize",required=false,defaultValue="10")  int pageSize) {
		
		
		if(ref_id==null||ref_id==0){
			
			return new JsonBody<>(-1,"ref_id 不能为空");
		}
		
		Page<Comment> list= articleService.findComments(ref_type, ref_id, pageIndex, pageSize);
		
		return new JsonBody<>(1,"获取数据成功",list);
	}
	
	/**
	 * 获取文章（单个）图片
	 * @param aid
	 * @return
	 */
	@RequestMapping("/get-article-images.action")
	public @ResponseBody  JsonBody<List<ArticleImage>> get_article_images(Long aid) {
		
		if(aid==null||aid==0){
			
			return new JsonBody<>(-1,"id 不能为空");
		}
	
		List<ArticleImage> list= articleService.get_images(aid);
		
		return new JsonBody<>(1,"获取数据成功",list);
	}
	
	@RequestMapping("/submit-new-comment.action")
	public @ResponseBody  JsonBody<String> submit_new_comment(HttpServletRequest request,
			@RequestParam(value="ref_type",required=false,defaultValue="0")Long ref_type,Long ref_id,String content) {
		
		if( ref_id ==null || ref_id == 0){
			
			return new JsonBody<>(-1,"ref_id 不能为空（0）");
		}
		if( StringUtils.isEmptyOrWhitespace(content)){
			
			return new JsonBody<>(-1,"content 不能为空");
		}
		
		if( content.length() > 512){
			
			return new JsonBody<>(-1,"content 不能超过512个汉字");
		}
		
	 	Comment comment=new Comment();
	 	comment.setContent(content);
	 	comment.setRefId(ref_id);
	 	comment.setRefType(ref_type);
	 	comment.setCreate_date(new Date());
	 	comment.setFromUser(getCurUserId());
	 	comment.setCreate_ip(request.getRemoteAddr());
	 	
	 	articleService.post_comment(comment);
	 	
		return new JsonBody<String>(1,"操作成功，下次登录请用新密码");
	}
	
	@RequestMapping("/delete-comment.action")
	public @ResponseBody  JsonBody<String> delete_comment(Long cid) {
		
		
		if( cid ==null ||cid ==0){
			
			return new JsonBody<>(-1,"cid 不能为空（0）");
		}
		
		articleService.del_comment(cid);
		
		return new JsonBody<String>(1,"删除评论成功");
	}
	
	
	
	
}
