/**
 * red-db  by sam @2018年7月9日  
 */
package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.RolePermission;
import org.cloud.db.sys.entity.UserPermission;
import org.springframework.data.domain.Page;

/**
 * @author sam
 *
 */
public interface PermissionService {

	public void delete(Long id);

	public Permission save(Permission w);

	public Permission findById(Long id);

	public Page<Permission> search(Permission m, int page, int pageSize);

	public List<Permission> findByPidAndType(Long pid, Integer mtype);
    
	public List<Permission> findByPidAndTypeAndStatus(Long pid, Integer mtype, Integer Status);
    
	public void deleteRolePermission(Long id);

	public RolePermission saveRolePermission(RolePermission w);

	public RolePermission findRolePermissionById(Long id);
	
	public List<RolePermission> findRolePermissionByRoldId(Long roldId);
	
	public RolePermission findRolePermissionByRoleIdAndPermissionId(Long roldId, Long permissionId);
	
	public void deleteUserPermission(Long id);

	public UserPermission saveUserPermission(UserPermission w);

	public UserPermission findUserPermissionById(Long id);

}
