/**
*@Author: sam
*@Date: 2018年3月30日
*@Copyright: 2018  All rights reserved.
*/
package org.cloud.backend.system.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;

import org.cloud.backend.system.comm.constant.Result;
import org.cloud.backend.system.comm.constant.ResultConstant;
import org.cloud.backend.system.controller.BaseController;
import org.cloud.backend.system.dao.sys.model.SysSystem;
import org.cloud.backend.system.dao.sys.model.SysSystemExample;
import org.cloud.backend.system.dao.sys.service.SysSystemService;
import org.cloud.core.validator.LengthValidator;

@Controller
@RequestMapping("/system")
public class SysSystemController extends BaseController{

	@Autowired
	private SysSystemService systemService;

	@RequiresPermissions("sys:system:read")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "/manage/system/index";
	}

	@RequiresPermissions("sys:system:read")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(
			@RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
			@RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
			@RequestParam(required = false, defaultValue = "", value = "search") String search,
			@RequestParam(required = false, value = "sort") String sort,
			@RequestParam(required = false, value = "order") String order) {
		SysSystemExample upmsSystemExample = new SysSystemExample();
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
			upmsSystemExample.setOrderByClause(sort + " " + order);
		}
		if (StringUtils.isNotBlank(search)) {
			upmsSystemExample.or()
					.andTitleLike("%" + search + "%");
		}
		List<SysSystem> rows = systemService.selectByExampleForOffsetPage(upmsSystemExample, offset, limit);
		long total = systemService.countByExample(upmsSystemExample);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", rows);
		result.put("total", total);
		return result;
	}

	@RequiresPermissions("sys:system:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create() {
		return "/manage/system/create";
	}

	@RequiresPermissions("sys:system:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Object create(SysSystem upmsSystem) {
		ComplexResult result = FluentValidator.checkAll()
				.on(upmsSystem.getTitle(), new LengthValidator(1, 20, "标题"))
				.on(upmsSystem.getName(), new LengthValidator(1, 20, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			return new Result(ResultConstant.INVALID_LENGTH, result.getErrors());
		}
		long time = System.currentTimeMillis();
		upmsSystem.setCtime(time);
		upmsSystem.setOrders(time);
		int count = systemService.insertSelective(upmsSystem);
		return new Result(ResultConstant.SUCCESS, count);
	}

	@RequiresPermissions("sys:system:delete")
	@RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
	@ResponseBody
	public Object delete(@PathVariable("ids") String ids) {
		int count = systemService.deleteByPrimaryKeys(ids);
		return new Result(ResultConstant.SUCCESS, count);
	}

	@RequiresPermissions("sys:system:update")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") int id, ModelMap modelMap) {
		SysSystem system = systemService.selectByPrimaryKey(id);
		modelMap.put("system", system);
		return "/manage/system/update";
	}

	@RequiresPermissions("sys:system:update")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@PathVariable("id") int id, SysSystem upmsSystem) {
		ComplexResult result = FluentValidator.checkAll()
				.on(upmsSystem.getTitle(), new LengthValidator(1, 20, "标题"))
				.on(upmsSystem.getName(), new LengthValidator(1, 20, "名称"))
				.doValidate()
				.result(ResultCollectors.toComplex());
		if (!result.isSuccess()) {
			 return new Result(ResultConstant.INVALID_LENGTH, result.getErrors());
		}
		upmsSystem.setSystemId(id);
		int count = systemService.updateByPrimaryKeySelective(upmsSystem);

		 return new Result(ResultConstant.SUCCESS, count);
	}
}

