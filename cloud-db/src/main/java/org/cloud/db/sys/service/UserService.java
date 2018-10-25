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

	public SysUser save(SysUser m);
	
	public void delete(Long id);
	
    public SysUser findUserByName(String username);
    public SysUser findUserById(Long user_id);
    
    public SysUser findUserByMobile(String mobile);

    public List<Permission> findPermissionsByUserId(Long systemId,Long userId);
    
    public Page<SysUser> search(SysUser m,int page, int pageSize);
    
    public List<SysRole> getRolesAll();
    
    public SysRole saveRole(SysRole m);
    public SysRole findRoleById(Long roleId);
    public SysRole findRoleByName(String name);
    public void deleteRole(Long roleId);
    
}
