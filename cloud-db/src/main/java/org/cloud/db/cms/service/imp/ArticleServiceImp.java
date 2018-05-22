package org.cloud.db.cms.service.imp;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.cloud.db.cms.entity.Article;
import org.cloud.db.cms.entity.ArticleImage;
import org.cloud.db.cms.entity.Comment;
import org.cloud.db.cms.repository.ArticleImageRepository;
import org.cloud.db.cms.repository.ArticleRepository;
import org.cloud.db.cms.repository.CommentRepository;
import org.cloud.db.cms.service.ArticleService;

@Component("articleService")
public class ArticleServiceImp implements ArticleService{

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ArticleImageRepository articleImageRepository;
	
	@Override
	public Article save(Article m) {

		return articleRepository.save(m);
	}

	@Override
	public void delete(Long id) {

		articleRepository.delete(id);
	}

	@Override
	public Article findById(Long id) {
		
		return articleRepository.findById(id);
		
	}

	@Override
	public Page<Article> getByStatus(Integer status, int page, int pageSize) {
		
		return articleRepository.findByStatusOrderByIdDesc(status,new PageRequest(page, pageSize));
	}
	
	@Override
	public Page<Article> searchBy(final Article m,int page,int pageSize){
		
		return articleRepository.findAll(new Specification<Article>() {

			public Predicate toPredicate(Root<Article> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				String title = m.getTitle();

				String short_content = m.getShort_content();
				Integer status = m.getStatus(); //状态
				Integer cateId=m.getCateId();
				
				Predicate p1,p2,p3,p4,pc;

				if (!StringUtils.isEmpty(title)) {
					p1 = cb.like(root.get("title").as(String.class), "%"
							+ title.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "0");
					pc=cb.and(p1);
				
				}
				
				if(!StringUtils.isEmpty(short_content)){
					
					p2 = cb.like(root.get("short_content").as(String.class), "%"
							+ short_content.toLowerCase() + "%");
					
					pc=cb.and(pc,p2);
				}
				
				if(status != null){
	
					p4 =cb.equal(root.get("status").as(Integer.class),status);
					pc=cb.and(pc,p4);
				}
				
				if(cateId != null&&cateId != -1){
					
					p3 =cb.equal(root.get("cateId").as(Integer.class),cateId);
					pc=cb.and(pc,p3);
				}
				
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.desc(root.get("id").as(Long.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}
	
	@Override
	public Comment post_comment(Comment m) {
		
		return commentRepository.save(m);
	}

	@Override
	public void del_comment(Long cid) {

		commentRepository.delete(cid);
	}

	@Override
	public Page<Comment> findComments(Long ref_type,Long ref_id, int page, int pageSize) {
		
		return commentRepository.findByRefTypeAndRefIdOrderByIdDesc(ref_type, ref_id, new PageRequest(page, pageSize));
	}

	@Override
	public ArticleImage save_articleImage(ArticleImage m) {
		
		return articleImageRepository.save(m);
	}

	@Override
	public void del_artileImage(Long id) {
		articleImageRepository.delete(id);;
	}

	@Override
	public List<ArticleImage> get_images(long aid) {
		
		return articleImageRepository.findByArticleIdOrderByIdDesc(aid);
	}
	
	@Override
	public ArticleImage get_articleImage(Long artileimage_id){
		
		return articleImageRepository.findOne(artileimage_id);
	}
}
