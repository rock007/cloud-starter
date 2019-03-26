package org.cloud.db.sys.service.imp;

import java.util.List;

import org.cloud.db.sys.entity.RoleMenu;
import org.cloud.db.sys.entity.SysMenu;
import org.cloud.db.sys.repository.RoleMenuRepository;
import org.cloud.db.sys.repository.SysMenuRepository;
import org.cloud.db.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("sysMenuService")
public class SysMenuServiceImp implements SysMenuService{

	@Autowired
	private SysMenuRepository sysMenuRepository;
	
	@Autowired
	private RoleMenuRepository roleMenuRepository;
	
	@Override
	public void delete(Long id) {

		sysMenuRepository.deleteById(id);
	}

	@Override
	public SysMenu save(SysMenu w) {

		return sysMenuRepository.save(w);
	}

	@Override
	public SysMenu findById(Long id) {

		return sysMenuRepository.findById(id).orElse(null);
	}
	
	@Override
	public SysMenu findByName(String name) {
		
		List<SysMenu> list=sysMenuRepository.findByName(name);
		
		return list.size()>0?list.get(0):null;
	}

	@Override
	public SysMenu findByUrl(String url) {
		
		List<SysMenu> list=sysMenuRepository.findByUrl(url);
		
		return list.size()>0?list.get(0):null;
	}

	@Override
	public List<SysMenu> findByPid(Long pid) {
		
		return sysMenuRepository.findByPid(pid);
	}

	@Override
	public void deleteRoleMenu(Long id) {

		roleMenuRepository.deleteById(id);
	}

	@Override
	public RoleMenu saveRoleMenu(RoleMenu w) {
		
		return roleMenuRepository.save(w);
	}

	@Override
	public RoleMenu findRoleMenuById(Long id) {
		
		return roleMenuRepository.findById(id).orElse(null);
	}

	@Override
	public List<RoleMenu> findRoleMenuByRoleId(Long roleId) {
		
		return roleMenuRepository.findByRoleId(roleId);
	}

	
}
