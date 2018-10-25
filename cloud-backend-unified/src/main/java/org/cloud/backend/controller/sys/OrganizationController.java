package org.cloud.backend.controller.sys;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.Organization;
import org.cloud.db.sys.entity.SysSystem;
import org.cloud.db.sys.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/sys")
public class OrganizationController  extends BaseController{

	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping("/organization-list.html")
	public String permission_list() {
		
		return "pages/sys/organization-list";
	}
	
	@RequestMapping("/organization-edit.html")
	public String organization_edit(Model model,Long id) {
		
		Organization m=new Organization();
		if(!(id==null||id==0)) {
			
			m= organizationService.get(id);
			
		}else {
			model.addAttribute("err", "记录不存在");
		}
		model.addAttribute("m", m==null?new Organization():m);
		
		return "pages/sys/organization-edit";
	}
	
	
	@GetMapping("/get-organization.json")
	public @ResponseBody JsonBody<List<Organization>> get_organiztion_list(Long pid){
		
		if(pid==null) pid=0L;
		
		List<Organization> list= organizationService.getByPid(pid);
		
		return new JsonBody<>(1,"success",list);
		
	}
	
	@GetMapping("/search-organization.json")
	public @ResponseBody JsonBody<Page<Organization>> search_organiztion(Organization organization,
			@RequestParam(value="page",required=false,defaultValue="0")  int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="20")  int pageSize){
		
		Page<Organization> page= organizationService.search(organization, pageIndex, pageSize);
		
		return new JsonBody<>(1,"success",page);
	}
	
	@RequestMapping(value="/organization-edit.json",method=RequestMethod.POST)
	public @ResponseBody  JsonBody<Organization> submit_org(@ModelAttribute Organization m) {
		
		Organization saveOne=null;
		
		if(StringUtils.isEmpty(m.getName()) ){
			
			return new JsonBody<>(-1,"标题 不能为空");
		}
	  
		if(m.getPid()==null||m.getPid()<=0){
			
			return new JsonBody<>(-1,"上级不能为空");
		}
		
		if(m.getOrganizationId()!=null&&m.getOrganizationId()>0){
			
			Organization existOne=organizationService.get(m.getOrganizationId());
			if(existOne!=null){
				 BeanUtils.copyProperties(m,existOne,"ctime");
			}else{
				return new JsonBody<>(-2,"参数错误，信息不存在");	
			}
			//编辑
			
			saveOne=organizationService.save(existOne);
		}else{
			//添加
			m.setCtime(new Date().getTime());
			
			saveOne=organizationService.save(m);
		}
		
		return new JsonBody<>(1,"操作成功",saveOne);
	}
	
	@RequestMapping(value="/organization-rm.json",method=RequestMethod.POST)
	public @ResponseBody  JsonBody<String> delete_org( Long id) {
	
		if(id==null){
			
			return new JsonBody<>(-1,"id不能为空");
		}
		Organization one= organizationService.get(id);
		
		if(one==null){
			return new JsonBody<>(-2,"记录不存在");
		}
		
		List<Organization> list= organizationService.getByPid(id);
		
		if(list.size()>0){
			return new JsonBody<>(-2,"该组织存在子项，不能删除");
		}
		
		/**
		List<OrgUser> users= orgService.getUsersByOrgId(id);
		
		if(users.size()>0){
			return new JsonBody<>(-2,"该组织存在用户关联，不能删除");
		}
		***/
		organizationService.delete(id);
		
		return new JsonBody<>(1,"操作成功");
	}
	
	@RequestMapping("/organization-permission.html")
	public String organization_permission() {
		
		return "pages/sys/organization-permission";
	}
}
