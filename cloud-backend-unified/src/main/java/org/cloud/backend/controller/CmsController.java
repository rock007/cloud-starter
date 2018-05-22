package org.cloud.backend.controller;

import org.cloud.core.base.BaseController;
import org.cloud.db.cms.entity.Article;
import org.cloud.db.cms.entity.ArticleImage;
import org.cloud.db.cms.entity.Comment;
import org.cloud.db.cms.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/cms")
public class CmsController extends BaseController {

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/article-list.html")
	public String article_list(Map<String, Object> model){

		return "pages/cms/article-list";
	}
	
	@RequestMapping("/article-edit.html")
	public String article_edit(Map<String, Object> model,Long id){

		Article oneArticle= articleService.findById(id);
		List<ArticleImage> images= articleService.get_images(id);
		
		model.put("one", oneArticle);
		model.put("pics", images);
		return "pages/cms/article-edit";
	}
	
	@RequestMapping("/article-new.html")
	public String article_new(Map<String, Object> model){

		return "pages/cms/article-new";
	}
		
	@RequestMapping("/{title}-{id}.html")
	public String artice_detail(Map<String, Object> model,@PathVariable String title,@PathVariable Long id){
		
		Article oneArticle= articleService.findById(id);
		List<ArticleImage> images= articleService.get_images(id);
		Page<Comment> comment_page=articleService.findComments(0L, id, 1, 100);
		
		model.put("one", oneArticle);
		model.put("pics", images);
		model.put("comments", comment_page.getContent());
		
		return "blog/detail";
	}
	
}
