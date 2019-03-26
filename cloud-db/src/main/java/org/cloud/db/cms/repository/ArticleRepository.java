package org.cloud.db.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.cloud.db.cms.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long>,
											JpaSpecificationExecutor<Article> {
	
	
	Page<Article> findByStatusOrderByIdDesc(Integer status, Pageable pageable);
	
	Page<Article> findByCreateUserOrderByIdDesc(String username, Pageable pageable);
	
}