package org.cloud.backend.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.SysRole;
import org.cloud.db.sys.service.PermissionService;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/sys")
public class RoleController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/role-list.html")
	public String role_list() {
		
		return "pages/sys/role-list";
	}
	
	@GetMapping("/role-list.json")
	public @ResponseBody JsonBody<List<SysRole>> get_role_list(
			@RequestParam(value="pageNo",required=false,defaultValue="0")  int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="20")  int pageSize){
		
		List<SysRole> list= userService.getRolesAll();
		
		return new JsonBody<>(1,"success",list);
	}
	
	@RequestMapping("/role-permission.html")
	public String role_permission(Model model) {
		
		List<SysRole> list= userService.getRolesAll();
		
		Map<Long,Object> map=new HashMap<>();
		
		for(SysRole role:list) {
			
			map.put(role.getRoleId(), permissionService.findRolePermissionByRoldId(role.getRoleId()));
		}
		
		model.addAttribute("roles", list);
		model.addAttribute("rolePermissionMap", map);
		
		return "pages/sys/role-permission";
	}
}
