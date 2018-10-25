package org.cloud.db.sys.repository;

import java.util.List;

import org.cloud.db.sys.entity.RoleMenu;
import org.springframework.data.repository.CrudRepository;

public interface RoleMenuRepository extends CrudRepository<RoleMenu, Long> {

	List<RoleMenu> findByRoleId(Long roleId);
}
