package org.cloud.backend.system.dao.sys.service;

import java.util.List;
import java.util.Map;

import org.cloud.backend.system.comm.base.BaseService;
import org.cloud.backend.system.dao.sys.model.SysPermission;
import org.cloud.backend.system.dao.sys.model.SysPermissionExample;

/**
* SysPermissionService接口
* sam is here  2017/3/20.
*/
public interface SysPermissionService extends BaseService<SysPermission, SysPermissionExample> {

	List<Map<String,Object>> getTreeByRoleId(Integer roleId);

	List<Map<String,Object>> getTreeByUserId(Integer usereId, Byte type);

}