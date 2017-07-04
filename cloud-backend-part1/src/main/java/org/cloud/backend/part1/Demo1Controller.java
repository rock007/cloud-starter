package org.cloud.backend.part1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/demo1")
public class Demo1Controller {

	@RequestMapping(value="/")
	public String index(){
		
		return "/demo1/index";
	}
	
	@RequestMapping(value="/test.json")
	public @ResponseBody String test1(){
		return "i am ooook";
	}
}
