package org.cloud.db.sys.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.UploadFileRelate;


public interface UploadFileRelateRepository extends CrudRepository<UploadFileRelate, Long> {
	
	List<UploadFileRelate> findByRefTypeAndRefId(Integer ref_type,Long ref_id);
}