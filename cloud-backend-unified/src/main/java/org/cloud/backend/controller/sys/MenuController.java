package org.cloud.backend.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysMenu;
import org.cloud.db.sys.entity.SysRole;
import org.cloud.db.sys.entity.SysSystem;
import org.cloud.db.sys.service.ActLogService;
import org.cloud.db.sys.service.SysMenuService;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class MenuController  extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("/menu-list.html")
	public String menu_list() {
		
		return "pages/sys/menu-list";
	}
	
	@GetMapping("/menu-list.json")
	public @ResponseBody JsonBody<List<SysMenu>> get_menu_list(SysMenu sysMenu,
			@RequestParam(value="pageNo",required=false,defaultValue="0")  int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="20")  int pageSize){
		
		List<SysMenu> list= sysMenuService.findByPid(sysMenu.getMenuId());
		
		return new JsonBody<>(1,"success",list);
	}
	
	@RequestMapping("/role-menu.html")
	public String role_menu(Model model) {
		
		List<SysRole> list= userService.getRolesAll();
		
		Map<Long,Object> map=new HashMap<>();
		
		for(SysRole role:list) {
			
			map.put(role.getRoleId(), sysMenuService.findRoleMenuByRoleId(role.getRoleId()));
		}
		
		model.addAttribute("roles", list);
		model.addAttribute("roleMenuMap", map);
		return "pages/sys/role-menu";
	}
	
	@RequestMapping("/menu-edit.html")
	public String get_menu_eidt(Model model,Long id) {
		
		if(!(id==null||id==0)) {
			
			SysMenu m= sysMenuService.findById(id);;
			
			model.addAttribute("m", m);
		}else {
			
			model.addAttribute("m", new SysMenu());
			model.addAttribute("err", "记录不存在");
		}
		
		return "pages/sys/menu-eidt";
	}
	
	@PostMapping("/menu-edit.json")
	public JsonBody<String> post_menu_submit(SysMenu sysMenu){

		if (StringUtils.isEmptyOrWhitespace(sysMenu.getName())) {

			return new JsonBody<>(-1, "名称不能为空");
		}
		if (StringUtils.isEmptyOrWhitespace(sysMenu.getUrl())) {

			return new JsonBody<>(-1, "url不能为空");
		}
		if (StringUtils.isEmptyOrWhitespace(sysMenu.getTarget())) {

			return new JsonBody<>(-1, "target不能为空");
		}
		if (sysMenu.getPid()==null) {

			return new JsonBody<>(-1, "上一级 不能为空");
		}
		
		SysMenu existOne;
		if (sysMenu.getMenuId() != null && sysMenu.getMenuId() > 0) {

			// 编辑
			existOne = sysMenuService.findById(sysMenu.getMenuId());

			if (existOne == null) {

				return new JsonBody<>(-1, "记录不存在或被删除！");
			}

		} else {
			// 添加
			existOne = sysMenuService.findByName(sysMenu.getName());
			if (existOne != null) {

				return new JsonBody<>(-1, "名称已存在！");
			}
			
			existOne = sysMenuService.findByUrl(sysMenu.getUrl());
			if (existOne != null) {

				return new JsonBody<>(-1, "二级域名已存在！");
			}
		}
		
		sysMenuService.save(sysMenu);
		return new JsonBody<>(1, "操作成功");
	}
	
	@PostMapping("/menu-rm.json")
	public @ResponseBody JsonBody<String> post_menu_rm(Long id){
	
		if(id==null||id==0) {
			
			return new JsonBody<>(-1, "id不能为空");
		}
		
		//TODO 检查关联，禁止删除
		
		sysMenuService.delete(id);
		
		return new JsonBody<>(1, "操作成功");
	}
}
