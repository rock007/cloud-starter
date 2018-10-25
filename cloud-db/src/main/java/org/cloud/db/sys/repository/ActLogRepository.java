package org.cloud.db.sys.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.ActErrLog;

public interface ActLogRepository extends CrudRepository<ActErrLog, Long> ,JpaSpecificationExecutor<ActErrLog> {
	
	ActErrLog findById(Long id);
}