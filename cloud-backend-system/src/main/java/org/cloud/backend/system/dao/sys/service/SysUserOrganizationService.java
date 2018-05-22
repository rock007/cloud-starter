package org.cloud.backend.system.dao.sys.service;


import org.cloud.backend.system.comm.base.BaseService;
import org.cloud.backend.system.dao.sys.model.SysUserOrganization;
import org.cloud.backend.system.dao.sys.model.SysUserOrganizationExample;

/**
* SysUserOrganizationService接口
*/
public interface SysUserOrganizationService extends BaseService<SysUserOrganization, SysUserOrganizationExample> {

    /**
     * 用户组织
     * @param organizationIds 组织ids
     * @param id 用户id
     * @return
     */
    int organization(String[] organizationIds, int id);

}