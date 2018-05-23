package org.cloud.backend.system.dao.sys.service.imp;

import org.cloud.backend.system.comm.base.BaseServiceImpl;
import org.cloud.backend.system.dao.sys.mapper.SysPermissionMapper;
import org.cloud.backend.system.dao.sys.mapper.SysSystemMapper;
import org.cloud.backend.system.dao.sys.mapper.SysUserPermissionMapper;
import org.cloud.backend.system.dao.sys.model.*;
import org.cloud.backend.system.dao.sys.service.SysApiService;
import org.cloud.backend.system.dao.sys.service.SysPermissionService;
import org.cloud.core.annotation.MyBatisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* SysPermissionService实现
* sam is here  2017/3/20.
*/
@Service
@Transactional
@MyBatisService
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionMapper, SysPermission, SysPermissionExample> implements SysPermissionService {

    private static Logger _log = LoggerFactory.getLogger(SysPermissionServiceImpl.class);

    @Autowired
    SysSystemMapper sysSystemMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    SysApiService sysApiService;

    @Autowired
    SysUserPermissionMapper sysUserPermissionMapper;

    @Override
    public List<Map<String,Object>> getTreeByRoleId(Integer roleId) {
        // 角色已有权限
        List<SysRolePermission> rolePermissions = sysApiService.selectSysRolePermisstionBySysRoleId(roleId);

        List<Map<String,Object>> systems = new ArrayList<>();
        // 系统
        SysSystemExample sysSystemExample = new SysSystemExample();
        sysSystemExample.createCriteria()
                .andStatusEqualTo((byte) 1);
        sysSystemExample.setOrderByClause("orders asc");
        List<SysSystem> SysSystems = sysSystemMapper.selectByExample(sysSystemExample);
        for (SysSystem SysSystem : SysSystems) {
        	Map<String,Object> node = new HashMap<>();
            node.put("id", SysSystem.getSystemId());
            node.put("name", SysSystem.getTitle());
            node.put("nocheck", true);
            node.put("open", true);
            systems.add(node);
        }

        if (systems.size() > 0) {
            for (Map<String,Object> system: systems) {
                SysPermissionExample SysPermissionExample = new SysPermissionExample();
                SysPermissionExample.createCriteria()
                        .andStatusEqualTo((byte) 1)
                        .andSystemIdEqualTo((Integer)system.get("id"));
                
                SysPermissionExample.setOrderByClause("orders asc");
                List<SysPermission> SysPermissions = sysPermissionMapper.selectByExample(SysPermissionExample);
                if (SysPermissions.size() == 0) continue;
                // 目录
                List<Map<String,Object>> folders = new ArrayList<>();
                for (SysPermission SysPermission: SysPermissions) {
                    if (SysPermission.getPid().intValue() != 0 || SysPermission.getType() != 1) continue;
                    
                    Map<String,Object> node = new HashMap<>();
                    node.put("id", SysPermission.getPermissionId());
                    node.put("name", SysPermission.getName());
                    node.put("open", true);
                    for (SysRolePermission rolePermission : rolePermissions) {
                        if (rolePermission.getPermissionId().intValue() == SysPermission.getPermissionId().intValue()) {
                            node.put("checked", true);
                        }
                    }
                    folders.add(node);
                    // 菜单
                    List<Map<String,Object>> menus = new ArrayList<>();
                    for (Map<String,Object> folder : folders) {
                        for (SysPermission SysPermission2: SysPermissions) {
                            if (SysPermission2.getPid().intValue() != ( (Integer)folder.get("id")) || SysPermission2.getType() != 2) continue;
                            Map<String,Object> node2 = new HashMap<>();
                            node2.put("id", SysPermission2.getPermissionId());
                            node2.put("name", SysPermission2.getName());
                            node2.put("open", true);
                            for (SysRolePermission rolePermission : rolePermissions) {
                                if (rolePermission.getPermissionId().intValue() == SysPermission2.getPermissionId().intValue()) {
                                    node2.put("checked", true);
                                }
                            }
                            menus.add(node2);
                            // 按钮
                            List<Map<String,Object>> buttons = new ArrayList<>();
                            for (Map<String,Object> menu : menus) {
                                for (SysPermission SysPermission3: SysPermissions) {
                                    if (SysPermission3.getPid().intValue() != ((Integer) menu.get("id")) || SysPermission3.getType() != 3) continue;
                                    Map<String,Object> node3 = new HashMap<>();
                                    node3.put("id", SysPermission3.getPermissionId());
                                    node3.put("name", SysPermission3.getName());
                                    node3.put("open", true);
                                    for (SysRolePermission rolePermission : rolePermissions) {
                                        if (rolePermission.getPermissionId().intValue() == SysPermission3.getPermissionId().intValue()) {
                                            node3.put("checked", true);
                                        }
                                    }
                                    buttons.add(node3);
                                }
                                if (buttons.size() > 0) {
                                     menu.put("children", buttons);
                                    buttons = new ArrayList<Map<String,Object>>();
                                }
                            }
                        }
                        if (menus.size() > 0) {
                            folder.put("children", menus);
                            menus = new ArrayList<Map<String,Object>>();
                        }
                    }
                }
                if (folders.size() > 0) {
                     system.put("children", folders);
                }
            }
        }
        return systems;
    }

    @Override
    public List<Map<String,Object>> getTreeByUserId(Integer usereId, Byte type) {
        // 角色权限
        SysUserPermissionExample SysUserPermissionExample = new SysUserPermissionExample();
        SysUserPermissionExample.createCriteria()
                .andUserIdEqualTo(usereId)
                .andTypeEqualTo(type);
        List<SysUserPermission> SysUserPermissions = sysUserPermissionMapper.selectByExample(SysUserPermissionExample);

        List<Map<String,Object>> systems = new ArrayList<>();
        // 系统
        SysSystemExample SysSystemExample = new SysSystemExample();
        SysSystemExample.createCriteria()
                .andStatusEqualTo((byte) 1);
        SysSystemExample.setOrderByClause("orders asc");
        List<SysSystem> SysSystems = sysSystemMapper.selectByExample(SysSystemExample);
        for (SysSystem SysSystem : SysSystems) {
        	Map<String,Object> node = new HashMap<>();
            node.put("id", SysSystem.getSystemId());
            node.put("name", SysSystem.getTitle());
            node.put("nocheck", true);
            node.put("open", true);
            systems.add(node);
        }

        if (systems.size() > 0) {
            for (Map<String,Object> system: systems) {
                SysPermissionExample SysPermissionExample = new SysPermissionExample();
                SysPermissionExample.createCriteria()
                        .andStatusEqualTo((byte) 1)
                        .andSystemIdEqualTo((Integer) system.get("id"));
                SysPermissionExample.setOrderByClause("orders asc");
                List<SysPermission> SysPermissions = sysPermissionMapper.selectByExample(SysPermissionExample);
                if (SysPermissions.size() == 0) continue;
                // 目录
                List<Map<String,Object>> folders = new ArrayList<>();
                for (SysPermission SysPermission: SysPermissions) {
                    if (SysPermission.getPid().intValue() != 0 || SysPermission.getType() != 1) continue;
                    Map<String,Object> node = new HashMap<>();
                    node.put("id", SysPermission.getPermissionId());
                    node.put("name", SysPermission.getName());
                    node.put("open", true);
                    for (SysUserPermission SysUserPermission : SysUserPermissions) {
                        if (SysUserPermission.getPermissionId().intValue() == SysPermission.getPermissionId().intValue()) {
                            node.put("checked", true);
                        }
                    }
                    folders.add(node);
                    // 菜单
                    List<Map<String,Object>> menus = new ArrayList<>();
                    for (Map<String,Object> folder : folders) {
                        for (SysPermission SysPermission2: SysPermissions) {
                            if (SysPermission2.getPid().intValue() != (Integer) folder.get("id") || SysPermission2.getType() != 2) continue;
                            Map<String,Object> node2 = new HashMap<>();
                            node2.put("id", SysPermission2.getPermissionId());
                            node2.put("name", SysPermission2.getName());
                            node2.put("open", true);
                            for (SysUserPermission SysUserPermission : SysUserPermissions) {
                                if (SysUserPermission.getPermissionId().intValue() == SysPermission2.getPermissionId().intValue()) {
                                    node2.put("checked", true);
                                }
                            }
                            menus.add(node2);
                            // 按钮
                            List<Map<String,Object>> buttons = new ArrayList<>();
                            for (Map<String,Object> menu : menus) {
                                for (SysPermission SysPermission3: SysPermissions) {
                                    if (SysPermission3.getPid().intValue() != (Integer) menu.get("id") || SysPermission3.getType() != 3) continue;
                                    Map<String,Object> node3 = new HashMap<>();
                                    node3.put("id", SysPermission3.getPermissionId());
                                    node3.put("name", SysPermission3.getName());
                                    node3.put("open", true);
                                    for (SysUserPermission SysUserPermission : SysUserPermissions) {
                                        if (SysUserPermission.getPermissionId().intValue() == SysPermission3.getPermissionId().intValue()) {
                                            node3.put("checked", true);
                                        }
                                    }
                                    buttons.add(node3);
                                }
                                if (buttons.size() > 0) {
                                    menu.put("children", buttons);
                                    buttons = new ArrayList<Map<String,Object>>();
                                }
                            }
                        }
                        if (menus.size() > 0) {
                            folder.put("children", menus);
                            menus =new ArrayList<Map<String,Object>>();
                        }
                    }
                }
                if (folders.size() > 0) {
                    system.put("children", folders);
                }
            }
        }
        return systems;
    }

}