package org.cloud.backend.system.dao.sys.service;


import org.cloud.backend.system.comm.base.BaseService;
import org.cloud.backend.system.dao.sys.model.SysRole;
import org.cloud.backend.system.dao.sys.model.SysUser;
import org.cloud.backend.system.dao.sys.model.SysUserExample;

import java.util.List;

/**
* SysUserService接口
*/
public interface SysUserService extends BaseService<SysUser, SysUserExample> {

    SysUser createUser(SysUser SysUser);

    SysUser  selectUserByUsername(String name);

}