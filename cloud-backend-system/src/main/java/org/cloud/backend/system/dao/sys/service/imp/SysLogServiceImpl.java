package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysLogMapper;
import org.cloud.backend.system.dao.sys.model.SysLog;
import org.cloud.backend.system.dao.sys.model.SysLogExample;
import org.cloud.backend.system.dao.sys.service.SysLogService;
import org.cloud.core.annotation.MyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SysLogService实现
* sam is here  2017/3/20.
*/
@Service
@Transactional
@MyBatisService
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog, SysLogExample> implements SysLogService {

    private static Logger _log = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Autowired
    SysLogMapper sysLogMapper;

}