package org.cloud.backend.system.dao.sys.service;

import java.util.List;
import java.util.Map;

import org.cloud.backend.system.comm.base.BaseService;
import org.cloud.backend.system.dao.sys.model.SysRolePermission;
import org.cloud.backend.system.dao.sys.model.SysRolePermissionExample;

/**
* SysRolePermissionService接口
*/
public interface SysRolePermissionService extends BaseService<SysRolePermission, SysRolePermissionExample> {

    /**
     * 角色权限
     * @param datas 权限数据
     * @param id 角色id
     * @return
     */
    int rolePermission(List<Map<String,Object>> datas, int id);

}