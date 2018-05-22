package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysOrganizationMapper;
import org.cloud.backend.system.dao.sys.model.SysOrganization;
import org.cloud.backend.system.dao.sys.model.SysOrganizationExample;
import org.cloud.backend.system.dao.sys.service.SysOrganizationService;
import org.cloud.core.annotation.MyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SysOrganizationService实现
* sam is here  2017/3/20.
*/
@Service
@Transactional
@MyBatisService
public class SysOrganizationServiceImpl extends BaseServiceImpl<SysOrganizationMapper, SysOrganization, SysOrganizationExample> implements SysOrganizationService {

    private static Logger _log = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

    @Autowired
    SysOrganizationMapper sysOrganizationMapper;

}