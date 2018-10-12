/**
*@Author: sam
*@Date: 2017年11月21日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.backend.controller.api;

import java.util.Date;
import java.util.List;

import org.cloud.core.base.JsonBaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.SysDic;
import org.cloud.db.sys.service.CommService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@CrossOrigin( maxAge = 3600)
@Controller
@RequestMapping(value="/api")
public class CommRestController extends JsonBaseController{

	@Autowired
	protected CommService commService;
	
	@RequestMapping(value="/get-dicts-by.json",method=RequestMethod.GET)
	public @ResponseBody  JsonBody<List<SysDic>> get_dicts(@RequestParam(value="parentId",required=false,defaultValue="0") Long parentId) {
		
		
		List<SysDic> list= commService.getDictByParentId(parentId);
		
		return new JsonBody<>(1,"获取数据成功",list);
	} 
	
	@RequestMapping(value="/submit-dict.json",method=RequestMethod.POST)
	public @ResponseBody  JsonBody<String> submit_dict(SysDic m) {
		
		if( m.getParentId()==null){
			
			return new JsonBody<>(-1,"parentId 不能为空");
		}
		
		if(StringUtils.isEmptyOrWhitespace(m.getMkey())){
			
			return new JsonBody<>(-1,"mkey 不能为空");
		}
		
		if(StringUtils.isEmptyOrWhitespace(m.getText())){
			
			return new JsonBody<>(-1,"text 不能为空");
		}
		
		if(m.getDictId()!=null&&m.getDictId()>0){
			
			SysDic existOne=commService.getDictById(m.getDictId());
			if(existOne!=null){
				 BeanUtils.copyProperties(m,existOne,"create_date");
			}else{
				return new JsonBody<>(-2,"参数错误，信息不存在");	
			}
			//编辑
			commService.saveDict(existOne);
		}else{
			//添加
			m.setCreate_date(new Date());
			
			commService.saveDict(m);
		}
		
		return new JsonBody<String>(1,"操作成功");
	}
	
	@GetMapping("/delete-dict.json")
	public @ResponseBody  JsonBody<String> delete_dict(Long id) {
		
		if( id ==null ||id ==0){
			
			return new JsonBody<>(-1,"id 不能为空（0）");
		}
		
		SysDic existOne=commService.getDictById(id);
		existOne.setFlag(-1);
		commService.saveDict(existOne);
		
		return new JsonBody<String>(1,"删除成功");
	}
	
}

