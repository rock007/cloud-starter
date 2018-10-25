package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.RoleMenu;
import org.cloud.db.sys.entity.SysMenu;

public interface SysMenuService {

	public void delete(Long id);

	public SysMenu save(SysMenu w);

	public SysMenu findById(Long id);
	
	public SysMenu findByName(String name);
	
	public SysMenu findByUrl(String url);
	
	public List<SysMenu> findByPid(Long pid);

	public void deleteRoleMenu(Long id);

	public RoleMenu saveRoleMenu(RoleMenu w);

	public RoleMenu findRoleMenuById(Long id);
	
	public List<RoleMenu> findRoleMenuByRoleId(Long roleId);
	
}
