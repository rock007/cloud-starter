package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysUserRoleMapper;
import org.cloud.backend.system.dao.sys.model.*;
import org.cloud.backend.system.dao.sys.service.SysUserRoleService;
import org.cloud.core.annotation.MyBatisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* SysUserRoleService实现
* sam is here 2017/3/20.
*/
@Service
@Transactional
@MyBatisService
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole, SysUserRoleExample> implements SysUserRoleService {

    private static Logger _log = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int role(String[] roleIds, int id) {
        int result = 0;
        // 删除旧记录
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        sysUserRoleExample.createCriteria()
                .andUserIdEqualTo(id);
        sysUserRoleMapper.deleteByExample(sysUserRoleExample);
        // 增加新记录
        if (null != roleIds) {
            for (String roleId : roleIds) {
                if (StringUtils.isBlank(roleId)) {
                    continue;
                }
                SysUserRole SysUserRole = new SysUserRole();
                SysUserRole.setUserId(id);
                SysUserRole.setRoleId(NumberUtils.toInt(roleId));
                result = sysUserRoleMapper.insertSelective(SysUserRole);
            }
        }
        return result;
    }

    @Override
    public List<SysUserRole> selectUserRoleByUserId(Integer userId){

        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria()
                .andUserIdEqualTo(userId.intValue());

        return sysUserRoleMapper.selectByExample(example);

    }

}