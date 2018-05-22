package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysUser;

/**
 * Created by sam on 2017/7/10.
 */
public interface UserService {

    public SysUser findUserByName(String username);
    public SysUser findUserById(Long user_id);

    public List<Permission> findPermissionsByUserId(Long userId);
}
