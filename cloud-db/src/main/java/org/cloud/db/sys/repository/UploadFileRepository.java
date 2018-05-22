package org.cloud.db.sys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.UploadFile;


public interface UploadFileRepository extends CrudRepository<UploadFile, String> {
	
	Page<UploadFile> findByStatusOrderByIdDesc(Integer status,Pageable pageable);
}