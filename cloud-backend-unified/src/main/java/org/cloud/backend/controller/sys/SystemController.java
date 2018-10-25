package org.cloud.backend.controller.sys;

import java.util.List;

import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.SysSystem;
import org.cloud.db.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping(value="/sys")
public class SystemController   extends BaseController{

	@Autowired
	private SystemService systemService;
	
	@RequestMapping("/system-list.html")
	public String system_list() {
		
		return "pages/sys/system-list";
	}
	
	@GetMapping("/system-list.json")
	public @ResponseBody JsonBody<List<SysSystem>> get_system_list(){
		
		List<SysSystem> list= systemService.getAll();
		
		return new JsonBody<>(1,"success",list);
	}
	
	@RequestMapping("/system-edit.html")
	public String system_edit(Model model, Long systemId) {
		
		if(!(systemId==null||systemId==0)) {
			
			SysSystem m= systemService.getSystemById(systemId);
			
			model.addAttribute("m", m);
		}else {
			
			model.addAttribute("m", new SysSystem());
			model.addAttribute("err", "记录不存在");
		}
		
		return "pages/sys/system-edit";
	}
	
	@PostMapping("/system-edit.json")
	public @ResponseBody JsonBody<String> post_system_edit(SysSystem system) {

		if (StringUtils.isEmptyOrWhitespace(system.getName())) {

			return new JsonBody<>(-1, "名称不能为空");
		}
		if (StringUtils.isEmptyOrWhitespace(system.getSecDomain())) {

			return new JsonBody<>(-1, "二级域名不能为空");
		}
		if (StringUtils.isEmptyOrWhitespace(system.getHost())) {

			return new JsonBody<>(-1, "host 不能为空");
		}
		
		SysSystem existOne;
		if (system.getSystemId() != null && system.getSystemId() > 0) {

			// 编辑
			existOne = systemService.getSystemById(system.getSystemId());

			if (existOne == null) {

				return new JsonBody<>(-1, "记录不存在或被删除！");
			}

		} else {
			// 添加
			existOne = systemService.getSystemByName(system.getName());
			if (existOne != null) {

				return new JsonBody<>(-1, "名称已存在！");
			}
			
			existOne = systemService.getSystemBySecDomain(system.getSecDomain());
			if (existOne != null) {

				return new JsonBody<>(-1, "二级域名已存在！");
			}
			
		}
		
		systemService.saveSystem(system);

		return new JsonBody<>(1, "操作成功");
	}
	
	@PostMapping("/system-rm.json")
	public @ResponseBody JsonBody<String> post_system_rm(Long systemId) {
		
		if(systemId==null||systemId==0) {
			
			return new JsonBody<>(-1, "应用id不能为空");
		}
		
		//TODO 检查关联，禁止删除
		
		systemService.deleteSystem(systemId);
		
		return new JsonBody<>(1, "操作成功");
	}

	
}
