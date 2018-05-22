package org.cloud.shop.controller;

import org.cloud.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
public class ShopController extends BaseController {

	@RequestMapping("/")
	public String backend(Map<String, Object> model){

		return "index";
	}

	@RequestMapping("/dashboard")
	public String desktop(Map<String, Object> model){

		return "dashboard";
	}

	@RequestMapping("/dashboard/index.html")
	public String dashboard_index(Map<String, Object> model){

		return "pages/index";
	}

}
