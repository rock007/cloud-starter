package org.cloud.backend.system.Interceptor;

import org.cloud.backend.system.dao.sys.model.SysUser;
import org.cloud.backend.system.dao.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台过滤器
 */
@Component
public class ManageInterceptor extends HandlerInterceptorAdapter {

	SysUserService sysUserService;

	public ManageInterceptor(SysUserService _userService){
		
		sysUserService=_userService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		//request.setAttribute("ALIYUN_OSS_POLICY", ALIYUN_OSS_POLICY);
		// 过滤ajax
		if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		}
		
		// 登录信息
		Subject subject = SecurityUtils.getSubject();
		
		if(subject.getPrincipal()!=null){
			String username = (String) subject.getPrincipal();
			SysUser curUser = sysUserService.selectUserByUsername(username);
			request.setAttribute("curUser", curUser);			
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}
