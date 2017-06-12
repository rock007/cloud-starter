package org.cloud.db.repository;

import org.cloud.db.entity.ActLog;
import org.springframework.data.repository.CrudRepository;

public interface ActLogRepository extends CrudRepository<ActLog, Long> {
	ActLog findById(Long id);
}