package org.cloud.cms.controller.backend;

import java.util.Map;

import org.cloud.core.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 上传的附件
 * @author sam
 *
 */
@Controller
@RequestMapping("/backend")
public class FilesController extends BaseController{

	@RequestMapping("/files-list.html")
	public String cate_list(Map<String, Object> model){

		return "pages/files/files-list";
	}
}
