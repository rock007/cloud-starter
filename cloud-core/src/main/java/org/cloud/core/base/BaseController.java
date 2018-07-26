package org.cloud.core.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.cloud.core.model.JsonBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import java.util.Date;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by sam on 2017/7/7.
 */
public abstract class BaseController {

    private final static Logger _log = LoggerFactory.getLogger(BaseController.class);
	
    
    @Value("${sso.server.url}")
	private String server_url;
    
    /**
     * 统一异常处理
     * @param request
     * @param response
     * @param exception
     */
    @ExceptionHandler
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        _log.error("统一异常处理：", exception);
        request.setAttribute("errorMessage", exception);
        
        request.setAttribute("time", new Date());
        //request.setAttribute("status",response.getStatus());
        request.setAttribute("url", request.getRequestURI()+"		queryStr:"+request.getQueryString());
	   
	    
        if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            request.setAttribute("requestHeader", "ajax");
            
            Gson gson = new Gson();
            JsonBody<String> err=new JsonBody<String>(-100,"系统出现错误",exception.getMessage());
            try {
            	response.setCharacterEncoding("UTF-8");
            	response.setContentType("application/json;charset=UTF-8");
            	response.getWriter().write(gson.toJson(err));
            	response.getWriter().flush();
            }catch(Exception ex) {
            	
            }
            return null;
        }

        // shiro没有权限异常
        if (exception instanceof UnauthorizedException
                ||exception instanceof UnauthenticatedException) {
            return "403";
        }
        // shiro会话已过期异常
        if (exception instanceof InvalidSessionException) {
            return "error";
        }
        return "error";
    }

    public  String getCurUserId(){

    	Subject subject = SecurityUtils.getSubject();
    	if(subject!=null&&subject.isAuthenticated()){
    		
    	 	return (String) subject.getPrincipal();// 获取用户id
    	}
    	
    	return null;
	}
    
    /***
     * not work
     * @param model
     */
    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("sso_host", server_url);
    }
}
