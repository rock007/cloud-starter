package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysRoleMapper;
import org.cloud.backend.system.dao.sys.model.SysRole;
import org.cloud.backend.system.dao.sys.model.SysRoleExample;
import org.cloud.backend.system.dao.sys.service.SysRoleService;
import org.cloud.core.annotation.MyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SysRoleService实现
*/
@Service
@Transactional
@MyBatisService
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole, SysRoleExample> implements SysRoleService {

    private static Logger _log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    SysRoleMapper sysRoleMapper;

}