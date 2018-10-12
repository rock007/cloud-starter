package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysRole;
import org.cloud.db.sys.entity.SysUser;
import org.springframework.data.domain.Page;

/**
 * Created by sam on 2017/7/10.
 */
public interface UserService {

    public SysUser findUserByName(String username);
    public SysUser findUserById(Long user_id);

    public List<Permission> findPermissionsByUserId(Long userId);
    
    public Page<SysUser> search(SysUser m,int page, int pageSize);
    
    public List<SysRole> getRolesAll();
}
