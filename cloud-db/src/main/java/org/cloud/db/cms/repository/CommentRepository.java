package org.cloud.db.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import org.cloud.db.cms.entity.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long> {


	Page<Comment> findByRefTypeAndRefIdOrderByIdDesc(Long ref_type,Long ref_id, Pageable pageable);
	
}