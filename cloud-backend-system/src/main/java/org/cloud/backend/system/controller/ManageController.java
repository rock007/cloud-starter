package org.cloud.backend.system.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.cloud.backend.system.dao.sys.model.SysPermission;
import org.cloud.backend.system.dao.sys.model.SysSystem;
import org.cloud.backend.system.dao.sys.model.SysSystemExample;
import org.cloud.backend.system.dao.sys.model.SysUser;
import org.cloud.backend.system.dao.sys.service.SysApiService;
import org.cloud.backend.system.dao.sys.service.SysSystemService;

/**
 * 后台controller
 */
@Controller
public class ManageController  extends BaseController{

	@Autowired
	private SysSystemService sysSystemService;

	@Autowired
	private SysApiService sysApiService;
	
	@RequestMapping(value = "/")
	public String Home() {
		return "index";
	}
	/**
	 * 后台首页
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model modelMap) {

		// 已注册系统
		SysSystemExample upmsSystemExample = new SysSystemExample();
		upmsSystemExample.createCriteria().andStatusEqualTo((byte) 1);
		List<SysSystem> upmsSystems = sysSystemService.selectByExample(upmsSystemExample);
		modelMap.addAttribute("systems", upmsSystems);
		// 当前登录用户权限
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		SysUser user = sysApiService.selectSysUserByUsername(username);
		List<SysPermission> upmsPermissions = sysApiService.selectSysPermissionBySysUserId(user.getUserId());
		modelMap.addAttribute("permissions", upmsPermissions);
		
		return "/manage/index";
	}

}