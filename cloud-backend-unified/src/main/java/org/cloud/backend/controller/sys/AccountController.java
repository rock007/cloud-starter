package org.cloud.backend.controller.sys;

import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/sys")
public class AccountController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/account-list.html")
	public String account_list() {
		
		return "pages/sys/account-list";
	}
	
	@GetMapping("/account-edit.html")
	public String get_account_edit(Model model,Long uid){
		
		SysUser user= userService.findUserById(uid);
		
		if(user==null) user=new SysUser();
		model.addAttribute("m", user);
		return "pages/sys/account-edit";
	}
	
	@GetMapping("/account-list.json")
	public @ResponseBody JsonBody<Page<SysUser>> get_account_list(SysUser m,
			@RequestParam(value="pageNo",required=false,defaultValue="0")  int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="20")  int pageSize){
		
		Page<SysUser> page= userService.search(m, pageIndex, pageSize);
		
		return new JsonBody<>(1,"success",page);
	}
	
}
