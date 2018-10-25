package org.cloud.backend.controller;

import org.cloud.core.app.AppConst;
import org.cloud.core.base.BaseController;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysSystem;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.service.PermissionService;
import org.cloud.db.sys.service.SystemService;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;


@Controller
public class BackendController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/")
	public String backend(Map<String, Object> model){

		return "index";
	}

	@RequestMapping("/dashboard")
	public String desktop(Map<String, Object> model,Long sid,HttpSession session){

		if(sid==null||sid==0) {
			sid=1L;
		}
		SysSystem sys= systemService.getSystemById(sid);
		
		SysUser curUser= userService.findUserById(getCurUserId());
		
		//session.setAttribute(AppConst.CON_SESSION_USER_ROLE, roleName);
		
		session.setAttribute(AppConst.CON_SESSION_USER_NAME, curUser.getRealname());
		
		Permission root=new Permission();
		
		root.setPermissionId(0L);
		root.setName("root");
		root.setPid(0L);
		
		initChild(sid,root, getCurUserId());
		
		model.put("menus", root.getChild());
		
		List<SysSystem> list= systemService.getAll();
		model.put("systems", list);
		model.put("sys", sys);
		
		return "dashboard";
	}

	private void  initChild(Long systemId,Permission m, Long userId){
		
		List<Permission> childs=permissionService.find4Menu(systemId,userId,m.getPermissionId());
		
		for(Permission c:childs){
			
			initChild(systemId,c, userId);
		}
		
		m.setChild(childs);
		
	}

	@RequestMapping("/dashboard/index.html")
	public String dashboard_index(Map<String, Object> model){

		return "pages/index";
	}

	@RequestMapping("/crud.html")
	public String crud(Map<String, Object> model){

		return "pages/crud";
	}

}
