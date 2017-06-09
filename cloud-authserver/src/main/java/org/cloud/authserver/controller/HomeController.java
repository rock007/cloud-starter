package org.cloud.authserver.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("authorizationRequest")
public class HomeController {

	@RequestMapping("/")
	public String index(Map<String, Object> model) {

		model.put("time", new Date());
		model.put("message", "hello the world");
		return "index";
	}
/***
	@RequestMapping("/login")
	public String login(Map<String, Object> model) {

		return "login";
	}

	@RequestMapping("/logout.html")
	public String logout(Map<String, Object> model) {

		return "login";
	}
	***/
}
