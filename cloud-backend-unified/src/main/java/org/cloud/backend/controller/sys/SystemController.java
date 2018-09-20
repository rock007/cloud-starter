package org.cloud.backend.controller.sys;

import java.util.List;

import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.SysSystem;
import org.cloud.db.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	@PostMapping("/system-edit.json")
	public @ResponseBody JsonBody<String> post_system_edit(SysSystem system) {

		if (StringUtils.isEmptyOrWhitespace(system.getName())) {

			return new JsonBody<>(-1, "名称 不能为空");
		}

		if (system.getSystemId() != null && system.getSystemId() > 0) {

			// 编辑
			SysSystem existOne = systemService.getSystemById(system.getSystemId());

			if (existOne == null) {

				return new JsonBody<>(-1, "记录不存在或被删除！");
			}

		} else {
			// 添加

		}
		
		systemService.saveSystem(system);

		return new JsonBody<>(1, "操作成功");
	}
	
}
