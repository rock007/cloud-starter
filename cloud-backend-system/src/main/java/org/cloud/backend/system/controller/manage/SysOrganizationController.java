package org.cloud.backend.system.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;

import org.cloud.backend.system.comm.constant.Result;
import org.cloud.backend.system.comm.constant.ResultConstant;
import org.cloud.backend.system.controller.BaseController;
import org.cloud.backend.system.dao.sys.model.SysOrganization;
import org.cloud.backend.system.dao.sys.model.SysOrganizationExample;
import org.cloud.backend.system.dao.sys.service.SysOrganizationService;
import org.cloud.core.validator.LengthValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织controller
 * sam is here  2017/2/6.
 */
@Controller
@RequestMapping("/organization")
public class SysOrganizationController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(SysOrganizationController.class);

    @Autowired
    private SysOrganizationService sysOrganizationService;

    @RequiresPermissions("sys:organization:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/organization/index";
    }

    @RequiresPermissions("sys:organization:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        SysOrganizationExample sysOrganizationExample = new SysOrganizationExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            sysOrganizationExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            sysOrganizationExample.or()
                    .andNameLike("%" + search + "%");
        }
        List<SysOrganization> rows = sysOrganizationService.selectByExampleForOffsetPage(sysOrganizationExample, offset, limit);
        long total = sysOrganizationService.countByExample(sysOrganizationExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", rows);
        result.put("total", total);
        return result;
    }

    @RequiresPermissions("sys:organization:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/organization/create";
    }


    @RequiresPermissions("sys:organization:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Object create(SysOrganization sysOrganization) {
        ComplexResult result = FluentValidator.checkAll()
                .on(sysOrganization.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());


        if (!result.isSuccess()) {
            return new Result(ResultConstant.INVALID_LENGTH, result.getErrors());
        }
        long time = System.currentTimeMillis();
        sysOrganization.setCtime(time);
        int count = sysOrganizationService.insertSelective(sysOrganization);
        return new Result(ResultConstant.SUCCESS, count);
    }

    @RequiresPermissions("sys:organization:delete")
    @RequestMapping(value = "/delete/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = sysOrganizationService.deleteByPrimaryKeys(ids);
        return new Result(ResultConstant.SUCCESS, count);
    }

    @RequiresPermissions("sys:organization:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        SysOrganization organization = sysOrganizationService.selectByPrimaryKey(id);
        modelMap.put("organization", organization);
        return "/manage/organization/update";
    }

    @RequiresPermissions("sys:organization:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, SysOrganization SysOrganization) {
        ComplexResult result = FluentValidator.checkAll()
                .on(SysOrganization.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new Result(ResultConstant.INVALID_LENGTH, result.getErrors());
        }
        SysOrganization.setOrganizationId(id);
        int count = sysOrganizationService.updateByPrimaryKeySelective(SysOrganization);
        return new Result(ResultConstant.SUCCESS, count);
    }

}
