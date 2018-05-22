package org.cloud.backend.system.dao.sys.mapper;


import org.cloud.backend.system.dao.sys.model.SysPermission;
import org.cloud.backend.system.dao.sys.model.SysRole;

import java.util.List;

public interface SysApiMapper {

	// 根据用户id获取所拥有的权限
	List<SysPermission> selectPermissionByUserId(Integer userId);

	// 根据用户id获取所属的角色
	List<SysRole> selectRoleByUserId(Integer userId);
	
}