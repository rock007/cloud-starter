package org.cloud.cms.controller.backend;

import java.util.Map;

import org.cloud.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backend/")
public class ArticleController extends BaseController{

	@RequestMapping("/article-list.html")
	public String article_list(Map<String, Object> model){

		return "pages/article/article-list";
	}
}
