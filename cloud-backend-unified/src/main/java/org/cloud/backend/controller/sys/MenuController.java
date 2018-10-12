package org.cloud.backend.controller.sys;

import org.cloud.db.sys.service.ActLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/sys")
public class MenuController {

	@Autowired
	private ActLogService actLogService;
	
	@RequestMapping("/menu-list.html")
	public String account_list() {
		
		return "pages/sys/menu-list";
	}
	
}
