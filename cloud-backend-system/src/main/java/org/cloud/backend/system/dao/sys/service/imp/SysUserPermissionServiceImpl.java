package org.cloud.backend.system.dao.sys.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysUserPermissionMapper;
import org.cloud.backend.system.dao.sys.model.SysUserPermission;
import org.cloud.backend.system.dao.sys.model.SysUserPermissionExample;
import org.cloud.backend.system.dao.sys.service.SysUserPermissionService;
import org.cloud.core.annotation.MyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* SysUserPermissionService实现
*/
@Service
@Transactional
@MyBatisService
public class SysUserPermissionServiceImpl extends BaseServiceImpl<SysUserPermissionMapper, SysUserPermission, SysUserPermissionExample> implements SysUserPermissionService {

    private static Logger _log = LoggerFactory.getLogger(SysUserPermissionServiceImpl.class);

    @Autowired
    SysUserPermissionMapper sysUserPermissionMapper;

    @Override
    public int permission(JSONArray datas, int id) {
        for (int i = 0; i < datas.size(); i ++) {
            JSONObject json = datas.getJSONObject(i);
            if (json.getBoolean("checked")) {
                // 新增权限
                SysUserPermission sysUserPermission = new SysUserPermission();
                sysUserPermission.setUserId(id);
                sysUserPermission.setPermissionId(json.getIntValue("id"));
                sysUserPermission.setType(json.getByte("type"));
                sysUserPermissionMapper.insertSelective(sysUserPermission);
            } else {
                // 删除权限
                SysUserPermissionExample sysUserPermissionExample = new SysUserPermissionExample();
                sysUserPermissionExample.createCriteria()
                        .andPermissionIdEqualTo(json.getIntValue("id"))
                        .andTypeEqualTo(json.getByte("type"));
                sysUserPermissionMapper.deleteByExample(sysUserPermissionExample);
            }
        }
        return datas.size();
    }

}