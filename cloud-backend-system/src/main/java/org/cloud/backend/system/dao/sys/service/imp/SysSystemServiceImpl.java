package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysSystemMapper;
import org.cloud.backend.system.dao.sys.model.SysSystem;
import org.cloud.backend.system.dao.sys.model.SysSystemExample;
import org.cloud.backend.system.dao.sys.service.SysSystemService;
import org.cloud.core.annotation.MyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SysSystemService实现
*/
@Service
@Transactional
@MyBatisService
public class SysSystemServiceImpl extends BaseServiceImpl<SysSystemMapper, SysSystem, SysSystemExample> implements SysSystemService {

    private static Logger _log = LoggerFactory.getLogger(SysSystemServiceImpl.class);

    @Autowired
    SysSystemMapper sysSystemMapper;

}