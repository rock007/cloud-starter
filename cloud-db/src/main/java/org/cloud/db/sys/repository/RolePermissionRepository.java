package org.cloud.db.sys.repository;

import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.RolePermission;

import java.util.List;

/**
 * Created by sam on 2017/7/7.
 */
public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {

    public List<RolePermission> findRolePermissionByRoleId(Long roldId);

    public RolePermission findRolePermissionByRoleIdAndPermissionId(Long roldId,Long permissionId);

}
