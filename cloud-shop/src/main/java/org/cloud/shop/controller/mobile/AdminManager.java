package org.cloud.shop.controller.mobile;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/admin")
public class AdminManager {
	
	@RequestMapping("/")
	public @ResponseBody String index(Map<String, Object> model,HttpSession session) {
		
		return "next todo";
	}
}
