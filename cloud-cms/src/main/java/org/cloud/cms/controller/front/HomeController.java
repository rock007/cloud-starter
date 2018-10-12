package org.cloud.cms.controller.front;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String backend(Map<String, Object> model){

		return "index";
	}
}
