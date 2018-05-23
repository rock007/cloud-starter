package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysRolePermissionMapper;
import org.cloud.backend.system.dao.sys.model.SysRolePermission;
import org.cloud.backend.system.dao.sys.model.SysRolePermissionExample;
import org.cloud.backend.system.dao.sys.service.SysRolePermissionService;
import org.cloud.core.annotation.MyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* SysRolePermissionService实现
* sam is here  2017/3/20.
*/
@Service
@Transactional
@MyBatisService
public class SysRolePermissionServiceImpl extends BaseServiceImpl<SysRolePermissionMapper, SysRolePermission, SysRolePermissionExample> implements SysRolePermissionService {

    private static Logger _log = LoggerFactory.getLogger(SysRolePermissionServiceImpl.class);

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public int rolePermission(List<Map<String,Object>> datas, int id) {
        List<Integer> deleteIds = new ArrayList<>();
        for (int i = 0; i < datas.size(); i ++) {
        	Map<String,Object> json = datas.get(i);
            if (!((boolean)json.get("checked"))) {
                deleteIds.add((Integer)json.get("id"));
            } else {
                // 新增权限
                SysRolePermission SysRolePermission = new SysRolePermission();
                SysRolePermission.setRoleId(id);
                SysRolePermission.setPermissionId((Integer)json.get("id"));
                sysRolePermissionMapper.insertSelective(SysRolePermission);
            }
        }
        // 删除权限
        if (deleteIds.size() > 0) {
            SysRolePermissionExample SysRolePermissionExample = new SysRolePermissionExample();
            SysRolePermissionExample.createCriteria()
                    .andPermissionIdIn(deleteIds)
                    .andRoleIdEqualTo(id);
            sysRolePermissionMapper.deleteByExample(SysRolePermissionExample);
        }
        return datas.size();
    }

}