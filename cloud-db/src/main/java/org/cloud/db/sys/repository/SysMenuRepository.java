package org.cloud.db.sys.repository;

import java.util.List;

import org.cloud.db.sys.entity.SysMenu;
import org.springframework.data.repository.CrudRepository;

public interface SysMenuRepository extends CrudRepository<SysMenu, Long> {

	List<SysMenu> findByPid(Long pid);
	
	List<SysMenu> findByName(String name);
	
	List<SysMenu> findByUrl(String url);
}
