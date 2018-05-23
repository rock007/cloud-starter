package org.cloud.backend.system.dao.sys.service;

import java.util.List;
import java.util.Map;

import org.cloud.backend.system.comm.base.BaseService;
import org.cloud.backend.system.dao.sys.model.SysUserPermission;
import org.cloud.backend.system.dao.sys.model.SysUserPermissionExample;

/**
* SysUserPermissionService接口
*/
public interface SysUserPermissionService extends BaseService<SysUserPermission, SysUserPermissionExample> {

    /**
     * 用户权限
     * @param datas 权限数据
     * @param id 用户id
     * @return
     */
    int permission(List<Map<String,Object>> datas, int id);

}