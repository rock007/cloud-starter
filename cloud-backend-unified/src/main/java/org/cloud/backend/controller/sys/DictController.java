package org.cloud.backend.controller.sys;

import java.util.List;

import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.SysDic;
import org.cloud.db.sys.service.CommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/sys")
public class DictController {

	@Autowired
	private CommService commService;
	
	@RequestMapping("/dict-list.html")
	public String permission_list(Model model) {
		
		List<SysDic> list= commService.getDictByParentId(0);
		model.addAttribute("top0List", list);
		return "pages/sys/dict-list";
	}
			
	@GetMapping("/dict-list.json")
	public @ResponseBody JsonBody<List<SysDic>> get_dict_list(Long pid,
			@RequestParam(value="page",required=false,defaultValue="0")  int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="20")  int pageSize){
		
		List<SysDic> list= commService.getDictByParentId(pid);
		
		return new JsonBody<>(1,"success",list);
	}
	
}
