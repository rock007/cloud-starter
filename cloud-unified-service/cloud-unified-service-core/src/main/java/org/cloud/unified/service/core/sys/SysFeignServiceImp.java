package org.cloud.unified.service.core.sys;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.service.PermissionService;
import org.cloud.db.sys.service.UserService;
import org.cloud.unified.service.api.sys.SysFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysFeignServiceImp implements SysFeignService {

    @Autowired
    private UserService userService;
    
    @Override
    public SysUser findUserByName(String username) {
        return userService.findUserByName(username);
    }

    @Override
    public SysUser findUserById(Long user_id) {

        return userService.findUserById(user_id);
    }

    @Override
    public List<Permission> findPermissionsByUserId(Long systemId,Long userId) {
        return userService.findPermissionsByUserId(systemId,userId);
    }
}
