package org.cloud.core.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import org.cloud.core.model.JsonBody;
import org.cloud.core.shiro.jwt.JwtToken;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtAuthenticationFilter extends AuthenticatingFilter  {

    private static final String TOKEN = "Authorization";

    private List<String> allowedPaths;
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        String curUrl = getRequestUrl(request);

        boolean isfind= allowedPaths.stream().anyMatch(a->curUrl.indexOf(a)!=-1);
        
        if(isfind||curUrl.equals("/")) return true;
        
        Subject subject = getSubject(request, response);

        return subject.isAuthenticated();
        
    }
    
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 先从Header里面获取
        String token = httpRequest.getHeader(TOKEN);
        if(StringUtils.isEmpty(token)){
            // 获取不到再从Parameter中拿
            token = httpRequest.getParameter(TOKEN);
            // 还是获取不到再从Cookie中拿
            if(StringUtils.isEmpty(token)){
                Cookie[] cookies = httpRequest.getCookies();
                if(cookies != null){
                    for (Cookie cookie : cookies) {
                        if(TOKEN.equals(cookie.getName())){
                            token = cookie.getValue();
                            break;
                        }
                    }
                }
            }
        }
        return new JwtToken("","",token);
    }
  
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        
        Map<String,Object> data=new HashMap<>();
        data.put("timestamp", System.currentTimeMillis());
        data.put("path",getRequestUrl(request));
        
        try {
        	response.setCharacterEncoding("UTF-8");
        	response.setContentType("application/json;charset=UTF-8");
        	//!response.setHeader("Access-Control-Allow-Origin","*");
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(new JsonBody<>(-403,"登录失败，无权访问",data)));
            response.getWriter().flush();
        } catch (IOException e) {
        }
        return false;
    }
    
    /***
     * get full path
     * @param request
     * @return
     */
    private String getRequestUrl(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest)request;
        String queryString = req.getQueryString();
        queryString = org.apache.commons.lang3.StringUtils.isBlank(queryString)?"": "?"+queryString;
        return req.getRequestURI()+queryString;
    }
    
    public  boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))||
        		"app".equals(request.getHeader("from"));
    }

	public List<String> getAllowedPaths() {
		return allowedPaths;
	}

	public void setAllowedPaths(List<String> allowedPaths) {
		this.allowedPaths = allowedPaths;
	}
}
