package org.cloud.shop.controller;

import org.cloud.core.base.BaseController;
import org.cloud.unified.service.api.shop.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
public class ShopController extends BaseController {

    @Autowired
    OrderFeignService orderFeignService;

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

	@GetMapping(value = "/doTest")
	public @ResponseBody  String hello(){

        String m1=""; //orderFeignService.submitOrder("4444444");

        return m1;
    }
}
