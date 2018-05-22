package org.cloud.db.sys.repository;

import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.UserPermission;

import java.util.List;

/**
 * Created by sam on 2017/7/7.
 */
public interface UserPermissionRepository extends CrudRepository<UserPermission, Long> {

    List<UserPermission> findByUserId(Long userId);

}
