/**
*@Project: cloud-web
*@Author: sam
*@Date: 2017年6月8日
*@Copyright: 2017  All rights reserved.
*/
package com.scarecrow.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sam
 *
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(){
		
		return "index";
	}
}
