package org.cloud.backend.controller.sys;

import org.cloud.core.base.BaseController;
import org.cloud.db.sys.service.ActLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/sys")
public class MsgController  extends BaseController{

	@Autowired
	private ActLogService actLogService;
	
	@RequestMapping("/msg-list.html")
	public String msg_list() {
		
		return "pages/sys/msg-list";
	}
	
}
