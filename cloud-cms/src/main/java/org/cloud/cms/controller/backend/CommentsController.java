package org.cloud.cms.controller.backend;

import java.util.Map;

import org.cloud.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 评论
 * @author sam
 *
 */
@Controller
@RequestMapping("/backend")
public class CommentsController extends BaseController{

	@RequestMapping("/comment-list.html")
	public String cate_list(Map<String, Object> model){

		return "pages/comment/comment-list";
	}
}
