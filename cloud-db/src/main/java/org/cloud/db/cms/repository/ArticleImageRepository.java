package org.cloud.db.cms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.cloud.db.cms.entity.ArticleImage;


public interface ArticleImageRepository extends CrudRepository<ArticleImage, Long> {

	List<ArticleImage> findByArticleIdOrderByIdDesc(Long aid);
	
}