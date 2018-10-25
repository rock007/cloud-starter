package org.cloud.backend.controller.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.SysRole;
import org.cloud.db.sys.service.PermissionService;
import org.cloud.db.sys.service.SystemService;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping(value="/sys")
public class RoleController  extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private SystemService systemService;
	
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
	
	@RequestMapping("/role-edit.html")
	public String role_edit(Model model, Long roleId) {
		
		if(!(roleId==null||roleId==0)) {
			
			SysRole m= userService.findRoleById(roleId);
			
			model.addAttribute("m", m);
		}else {
			
			model.addAttribute("m", new SysRole());
			model.addAttribute("err", "记录不存在");
		}
		
		return "pages/sys/role-edit";
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
		model.addAttribute("systems", systemService.getAll());
		
		return "pages/sys/role-permission";
	}
	
	@PostMapping("/role-edit.json")
	public  @ResponseBody JsonBody<List<SysRole>> post_role_submit(SysRole sysRole){
		
		if (StringUtils.isEmptyOrWhitespace(sysRole.getName())) {

			return new JsonBody<>(-1, "名称不能为空");
		}
		
		if (StringUtils.isEmptyOrWhitespace(sysRole.getTitle())) {

			return new JsonBody<>(-1, "标题不能为空");
		}
		
		SysRole existOne;
		if (sysRole.getRoleId() != null && sysRole.getRoleId() > 0) {

			// 编辑
			existOne = userService.findRoleById(sysRole.getRoleId());

			if (existOne == null) {

				return new JsonBody<>(-1, "记录不存在或被删除！");
			}
			sysRole.setCtime(existOne.getCtime());

		} else {
			// 添加
			existOne = userService.findRoleByName(sysRole.getName());
			if (existOne != null) {

				return new JsonBody<>(-1, "名称已存在！");
			}
			sysRole.setCtime(new Date().getTime());
			
		}
		
		userService.saveRole(sysRole);

		return new JsonBody<>(1, "操作成功");
	}
	
	@PostMapping("/role-rm.json")
	public  @ResponseBody JsonBody<String> rm_role_submit(Long roleId){
		
		if(roleId==null||roleId==0) {
			
			return new JsonBody<>(-1, "roleId不能为空");
		}
		
		//TODO 检查关联，禁止删除
		
		userService.deleteRole(roleId);
		
		return new JsonBody<>(1, "操作成功");
	}
}
