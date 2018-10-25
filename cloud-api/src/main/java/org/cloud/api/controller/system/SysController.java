/**
 * red-web  by sam @2018年7月10日  
 */
package org.cloud.api.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cloud.core.base.JsonBaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.RolePermission;
import org.cloud.db.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author sam
 *
 */
@Api("系统设置")
@CrossOrigin( maxAge = 3600)
@Controller
@RequestMapping(value="/sys")
public class SysController extends JsonBaseController {

	@Autowired
	private PermissionService permissionService;
	
	@ApiIgnore
	@ApiOperation(value="系统权限")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="systemId",dataType="long",required=true,value="系统id"),
		@ApiImplicitParam(paramType="query",name="mtype",dataType="Integer",required=true,value="类型(1:目录,2:菜单,3:按钮)"),
	})
	@GetMapping("/get-permission.action")
	public @ResponseBody Permission get_permission(Long systemId,final Integer mtype) {
	
		Permission root=new Permission();
		if(mtype==null||mtype==0){
			
			return root;
		}
		
		root.setPermissionId(0L);
		root.setName("root");
		root.setPid(-1L);
		
		initChild(root, systemId,mtype);
		
		return root;
	}
	
	private void  initChild(Permission m,final Long systemId,final Integer mtype){
		
		List<Permission> childs= permissionService.findBySystemIdAndPidAndType(systemId,m.getPermissionId(),mtype);
		
		for(Permission c:childs){
			
			initChild(c,systemId, mtype);
		}
		
		m.setChild(childs);
		
	}
	
	@ApiIgnore
	@RequestMapping(value="/set-role-permission.action",method=RequestMethod.POST)
	public @ResponseBody
    JsonBody<String> set_role_permission(Long roleId, Long permissionId, Integer act){
	
		if(roleId==null||roleId==0){
			
			return new JsonBody<>(-1,"roleId 不能为空");
		}
		if(permissionId==null||permissionId==0){
			
			return new JsonBody<>(-1,"permissionId 不能为空");
		}
		
		RolePermission existOne=permissionService.findRolePermissionByRoleIdAndPermissionId(roleId,permissionId);
		
		if(act==1){
			
			if(existOne!=null){
				return new JsonBody<>(-100,"记录已经存在");
			}
			RolePermission w=new RolePermission();
			w.setRoleId(roleId);
			w.setPermissionId(permissionId);
			
			permissionService.saveRolePermission(w);
			
		}else if(act==-1){
			
			if(existOne==null){
				return new JsonBody<>(-100,"记录不存在");
			}
			permissionService.deleteRolePermission(existOne.getRolePermissionId());
		}
		
		return new JsonBody<>(1,"操作成功！");
	} 
}
