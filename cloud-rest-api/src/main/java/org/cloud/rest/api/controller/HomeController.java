package org.cloud.rest.api.controller;

import java.util.Date;
import java.util.Map;

import org.cloud.db.sys.service.ActLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@SessionAttributes("authorizationRequest")
public class HomeController {
	
	@Autowired
	private ActLogService actLogService;
	
	@RequestMapping("/")
	public String index(Map<String, Object> model) {

		model.put("time", new Date());
		model.put("message", "hello the world");
		return "index";
	}
	
	@RequestMapping("/demo")
	public @ResponseBody String demo(Map<String, Object> model) {

		return "index";
	}

	@RequestMapping("/login.html")
	public String login(Map<String, Object> model) {

		return "login";
	}

	@RequestMapping("/logout.html")
	public String logout(Map<String, Object> model) {

		return "login";
	}
	@RequestMapping("/oauth/confirm_access")
	public String confirm_access(){
		
		return "authorize";
	}

}
