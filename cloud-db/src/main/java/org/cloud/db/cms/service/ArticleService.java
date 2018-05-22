package org.cloud.db.cms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import org.cloud.db.cms.entity.Article;
import org.cloud.db.cms.entity.ArticleImage;
import org.cloud.db.cms.entity.Comment;

public interface ArticleService {

	public Article save(Article m);
	
	public void delete(Long id);
	
	public Article findById(Long id);
	
	public Page<Article> getByStatus(Integer status,int page,int pageSize);
	
	public Page<Article> searchBy(Article m,int page,int pageSize);
	
	//评论
	public Comment post_comment(Comment m);
	
	public void del_comment(Long cid);
	
	public Page<Comment> findComments(Long ref_type,Long ref_id, int page, int pageSize);
	
	//图片
	public ArticleImage save_articleImage(ArticleImage m);
	
	public void del_artileImage(Long artileimage_id);
	
	public ArticleImage get_articleImage(Long artileimage_id);
	
	public List<ArticleImage> get_images(long aid);
}
