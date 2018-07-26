package org.cloud.api.controller.cms;

import org.cloud.core.base.JsonBaseController;
import org.cloud.db.sys.service.ActLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class ArticleController extends JsonBaseController {
	
	@Autowired
	private ActLogService actLogService;

	
	@RequestMapping("/demo")
	public  String demo(Map<String, Object> model) {

		return "index";
	}

}
