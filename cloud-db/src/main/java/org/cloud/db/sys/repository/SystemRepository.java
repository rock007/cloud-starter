package org.cloud.db.sys.repository;

import org.cloud.db.sys.entity.SysSystem;
import org.springframework.data.repository.CrudRepository;

public interface SystemRepository   extends CrudRepository<SysSystem, Long> {

}
