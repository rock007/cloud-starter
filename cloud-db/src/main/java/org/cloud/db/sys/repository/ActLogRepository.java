package org.cloud.db.sys.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.ActLog;

public interface ActLogRepository extends CrudRepository<ActLog, Long> ,JpaSpecificationExecutor<ActLog> {
	
	ActLog findById(Long id);
}