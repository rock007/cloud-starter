package org.cloud.backend.controller.sys;

import org.cloud.core.base.BaseController;
import org.cloud.db.sys.service.ActLogService;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/sys")
public class LogController  extends BaseController{

	@Autowired
	private ActLogService actLogService;
	
	@RequestMapping("/log-list.html")
	public String account_list() {
		
		return "pages/sys/log-list";
	}
	
}
