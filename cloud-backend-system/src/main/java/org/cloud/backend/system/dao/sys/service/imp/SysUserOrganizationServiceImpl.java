package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysUserOrganizationMapper;
import org.cloud.backend.system.dao.sys.model.SysUserOrganization;
import org.cloud.backend.system.dao.sys.model.SysUserOrganizationExample;
import org.cloud.backend.system.dao.sys.service.SysUserOrganizationService;
import org.cloud.core.annotation.MyBatisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SysUserOrganizationService实现
*/
@Service
@Transactional
@MyBatisService
public class SysUserOrganizationServiceImpl extends BaseServiceImpl<SysUserOrganizationMapper, SysUserOrganization, SysUserOrganizationExample> implements SysUserOrganizationService {

    private static Logger _log = LoggerFactory.getLogger(SysUserOrganizationServiceImpl.class);

    @Autowired
    SysUserOrganizationMapper sysUserOrganizationMapper;

    @Override
    public int organization(String[] organizationIds, int id) {
        int result = 0;
        // 删除旧记录
        SysUserOrganizationExample sysUserOrganizationExample = new SysUserOrganizationExample();
        sysUserOrganizationExample.createCriteria()
                .andUserIdEqualTo(id);
        sysUserOrganizationMapper.deleteByExample(sysUserOrganizationExample);
        // 增加新记录
        if (null != organizationIds) {
            for (String organizationId : organizationIds) {
                if (StringUtils.isBlank(organizationId)) {
                    continue;
                }
                SysUserOrganization SysUserOrganization = new SysUserOrganization();
                SysUserOrganization.setUserId(id);
                SysUserOrganization.setOrganizationId(NumberUtils.toInt(organizationId));
                result = sysUserOrganizationMapper.insertSelective(SysUserOrganization);
            }
        }
        return result;
    }

}