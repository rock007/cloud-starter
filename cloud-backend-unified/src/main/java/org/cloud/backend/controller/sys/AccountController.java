package org.cloud.backend.controller.sys;

import java.util.Date;

import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysSystem;
import org.cloud.db.sys.entity.SysUser;
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
public class AccountController  extends BaseController{

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
	
	@PostMapping("/account-edit.json")
	public @ResponseBody JsonBody<String> post_account_edit(SysUser m){
		
		if (StringUtils.isEmptyOrWhitespace(m.getUsername())) {

			return new JsonBody<>(-1, "用户名不能为空");
		}
		
		if (StringUtils.isEmptyOrWhitespace(m.getPhone())) {

			return new JsonBody<>(-1, "手机号不能为空");
		}
		
		if (StringUtils.isEmptyOrWhitespace(m.getRealname())) {

			return new JsonBody<>(-1, "真实姓名不能为空");
		}
		
		SysUser existOne;
		if (m.getUserId() != null && m.getUserId() > 0) {

			// 编辑
			existOne = userService.findUserById(m.getUserId());

			if (existOne == null) {

				return new JsonBody<>(-1, "记录不存在或被删除！");
			}

			m.setAvatar(existOne.getAvatar());
			m.setCtime(existOne.getCtime());
			m.setSalt(existOne.getSalt());
			
		} else {
			
			// 添加
			existOne = userService.findUserByName(m.getUsername());
			if (existOne != null) {

				return new JsonBody<>(-1, "用户名已存在！");
			}
			
			existOne = userService.findUserByMobile(m.getPhone());
			if (existOne != null) {

				return new JsonBody<>(-1, "该手机号已存在！");
			}
			
			m.setAvatar("");
			m.setCtime(new Date().getTime());
			m.setSalt("fuckyou");
		}
		
		//TODO 加角色、组织
		
		userService.save(m);

		return new JsonBody<>(1, "操作成功");
	}
	
	@PostMapping("/account-rm.json")
	public @ResponseBody JsonBody<String> post_account_rm(String ids){
	
		Long uid;

		if(ids==null||"".equals(ids)) {
			
			return new JsonBody<>(-1, "ids不能为空");
		}
		
		String ids_arr[]=ids.split(",");
		
		for(String m:ids_arr) {
		
			if(m==null||"".equals(m)) continue;
			try {
				uid=Long.parseLong(m);
				
			}catch(Exception ex) {
				
				continue;
			}
			
			//TODO 检查关联，禁止删除
			
			userService.delete(uid);
		}
		
		return new JsonBody<>(1, "操作成功");
	}
	
}
