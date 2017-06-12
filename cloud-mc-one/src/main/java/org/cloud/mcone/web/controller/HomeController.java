package org.cloud.mcone.web.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("/")
	public  String index(Map<String, Object> model) {
		
		model.put("time", new Date());
		model.put("message", "hello the world");
		return "index";
	}
	
	
	@RequestMapping("/thing")
    public @ResponseBody MyThing thing() {
            return new MyThing();
    }

	
	public class MyThing{
		
		private int id;
		
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
}
