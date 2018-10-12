package org.cloud.db.sys.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.cloud.db.sys.entity.SysUser;

/**
 * Created by sam on 2017/7/7.
 */
public interface UserRepository extends CrudRepository<SysUser, Long> ,JpaSpecificationExecutor<SysUser> {

	SysUser findByUsername(String username);
}
