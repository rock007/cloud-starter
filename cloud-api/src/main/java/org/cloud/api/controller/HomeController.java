package org.cloud.api.controller;

import java.util.Map;

import org.cloud.core.base.JsonBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends JsonBaseController {

	@RequestMapping("/")
	public String index(Map<String, Object> model) {

		return "redirect:/swagger-ui.html";
	}

}
