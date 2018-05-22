package org.cloud.db.sys.repository;

import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.Role;

/**
 * Created by sam on 2017/7/7.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {


}
