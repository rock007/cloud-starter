package org.cloud.cms.controller.backend;

import java.util.Map;

import org.cloud.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 文章分类
 * @author sam
 *
 */
@Controller
@RequestMapping("/backend")
public class CategoryController extends BaseController{

	@RequestMapping("/cate-list.html")
	public String cate_list(Map<String, Object> model){

		return "pages/cate/cate-list";
	}
}
