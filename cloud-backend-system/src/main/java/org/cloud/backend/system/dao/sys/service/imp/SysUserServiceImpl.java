package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysUserMapper;
import org.cloud.backend.system.dao.sys.model.SysUser;
import org.cloud.backend.system.dao.sys.model.SysUserExample;
import org.cloud.backend.system.dao.sys.service.SysUserService;
import org.cloud.core.annotation.MyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
* SysUserService实现
*/
@Service
@Transactional
@MyBatisService
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser, SysUserExample> implements SysUserService {

    private static Logger _log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser createUser(SysUser SysUser) {
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria()
                .andUsernameEqualTo(SysUser.getUsername());
        long count = sysUserMapper.countByExample(sysUserExample);
        if (count > 0) {
            return null;
        }
        sysUserMapper.insert(SysUser);
        return SysUser;
    }

    @Override
    public  SysUser selectUserByUsername(String name){

        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria()
                .andUsernameEqualTo(name);
        List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}