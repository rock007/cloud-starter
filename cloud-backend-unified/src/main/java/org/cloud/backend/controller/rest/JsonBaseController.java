/**
*@Author: sam
*@Date: 2016年12月12日
*@Copyright: 2016  All rights reserved.
*/
package org.cloud.backend.controller.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.ActLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.bus.Event;
import reactor.bus.EventBus;


/**
 * @author sam
 *
 */
   public abstract class JsonBaseController  {

	protected static final Logger logger = LoggerFactory.getLogger(JsonBaseController.class);
	
	protected static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
	
	protected static SimpleDateFormat sd_yyyyMM = new SimpleDateFormat("yyyyMM");
	
	@Autowired
	EventBus eventBus;
	
	@ExceptionHandler
	public @ResponseBody  JsonBody<String> handleException(HttpServletRequest request, Exception ex){
	   
		logger.error("handleException", ex);
		
		String url=request.getRequestURI();
		
		if(url.length()>256){
			url=url.substring(url.length()-260);
		}
		
		addLog(url,"error",request.getQueryString(),ex.getLocalizedMessage(),request.getParameter("user_id"),request.getRemoteAddr());
		return new JsonBody<String>(-1,"操作失败，系统出现异常",ex.getMessage()); 
	 }

	protected void addLog(String title,String msg,String content,String error,String create_user,String  create_ip){
		
		ActLog log=new ActLog(title,msg,content,error,create_user,create_ip);
		eventBus.notify("quotes", Event.wrap(log));
	}

	public static String getCurDT(){	            
        return sd.format(new java.util.Date());
	}
	
	public static String getDTyyyyMM(Date d){	            
        return sd_yyyyMM.format(d);
	}
	
	public String getCurUserId() {

		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {

			return (String) subject.getPrincipal();// 获取用户id
		}

		return "";
	}

}
