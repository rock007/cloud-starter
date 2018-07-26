package org.cloud.backend.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.RolePermission;
import org.cloud.db.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping(value="/sys")
public class PermissionController  extends BaseController {

	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/permission-list.html")
	public String permission_list() {
		
		return "pages/permission/permission-list";
	}
			
	@GetMapping("/permission-list.json")
	public @ResponseBody JsonBody<Page<Permission>> get_permission_list(Permission permission,
			@RequestParam(value="page",required=false,defaultValue="0")  int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="20")  int pageSize){
		
		Page<Permission> page= permissionService.search(permission, pageIndex, pageSize);
		
		return new JsonBody<>(1,"success",page);
	}
	
	@PostMapping("/permission-edit.json")
	public @ResponseBody JsonBody<String> post_menu_edit(Permission permission) {

		if (StringUtils.isEmptyOrWhitespace(permission.getName())) {

			return new JsonBody<>(-1, "名称 不能为空");
		}

		if (permission.getPermissionId() != null && permission.getPermissionId() > 0) {

			// 编辑

			Permission existOne = permissionService.findById(permission.getPermissionId());

			if (existOne == null) {

				return new JsonBody<>(-1, "记录不存在或被删除！");
			}
			permission.setCtime(existOne.getCtime());
			permission.setSystem_id(existOne.getSystem_id());

			// 排除非更新字段
			// BeanUtils.copyProperties(existOne,permission,new String[]
			// {"permissionId","pid","name","type","permission_value","uri","icon","ctime"});

		} else {
			// 添加
			permission.setCtime(new Date().getTime());
			permission.setSystem_id(1L);
		}

		permissionService.save(permission);

		return new JsonBody<>(1, "操作成功");
	}

	@GetMapping("/get-permission.json")
	public @ResponseBody  Permission get_permission(final Integer mtype,final Integer status) {
	
		Permission root=new Permission();
		if(mtype==null||mtype==0){
			
			return root;
		}
		
		root.setPermissionId(0L);
		root.setName("root");
		root.setPid(-1L);
		
		initChild(root, mtype, status);
		
		return root;
	}
	
	private void  initChild(Permission m,final Integer mtype,final Integer status){
		
		List<Permission> childs= new ArrayList<>();
		
		if(status!=null){
			
			childs= permissionService.findByPidAndTypeAndStatus(m.getPermissionId(),mtype,status);
			
		}else{
			childs= permissionService.findByPidAndType(m.getPermissionId(),mtype);
			
		}
		
		for(Permission c:childs){
			
			initChild(c, mtype, status);
		}
		
		m.setChild(childs);
		
	}

	@RequestMapping(value="/set-role-permission.json",method=RequestMethod.POST)
	public @ResponseBody JsonBody<String> set_role_permission(Long roleId,Long permissionId,Integer act){
	
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
