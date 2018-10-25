package org.cloud.core.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.cloud.core.model.ActLogModel;
import org.cloud.core.model.JsonBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.bus.Event;
import reactor.bus.EventBus;

import javax.servlet.http.HttpServletRequest;

public abstract class  JsonBaseController {

    protected static final Logger logger = LoggerFactory.getLogger(JsonBaseController.class);

    @Autowired
	private EventBus eventBus;
	
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
		
		ActLogModel log=new ActLogModel(title,msg,content,error,create_user,create_ip);
		eventBus.notify("quotes", Event.wrap(log));
	}
	
	public Long getCurUserId() {

		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {

			return (Long) subject.getPrincipal();// 获取用户id
		}

		return null;
	}
	
	public String getLoginUid() {
		
		Long uid=getCurUserId();
		
		return uid==null?"":String.valueOf(uid);
	}

}
