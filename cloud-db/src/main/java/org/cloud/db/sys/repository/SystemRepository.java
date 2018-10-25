package org.cloud.db.sys.repository;

import java.util.List;

import org.cloud.db.sys.entity.SysSystem;
import org.springframework.data.repository.CrudRepository;

public interface SystemRepository   extends CrudRepository<SysSystem, Long> {

	List<SysSystem> findByName(String name);
	
	List<SysSystem> findBysecDomain(String domain);
}
