package org.cloud.db.sys.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import org.cloud.db.sys.entity.SysRole;

/**
 * Created by sam on 2017/7/7.
 */
public interface RoleRepository extends CrudRepository<SysRole, Long> {


	List<SysRole> findByName(String name);
	
}
