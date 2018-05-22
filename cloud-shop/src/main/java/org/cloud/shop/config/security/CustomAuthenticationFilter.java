package org.cloud.shop.config.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

public class CustomAuthenticationFilter extends AuthenticationFilter{

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        String curUrl = getRequestUrl(request);

        if(curUrl.indexOf("/resources/")>=0
                ||curUrl.indexOf("/plugins/")>=0 ||curUrl.indexOf("/fonts/")>=0
                ||curUrl.indexOf("/js/")>=0||curUrl.indexOf("/css/")>=0
                ||curUrl.indexOf("/images/")>=0
                ||curUrl.indexOf("/login.action")>=0
                ||curUrl.equals("/")        ) {
            return true;
        }

        Subject subject = getSubject(request, response);
        //Session session = subject.getSession();

        return subject.isAuthenticated();
        
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	
    	
    	  HttpServletRequest req=(HttpServletRequest)request;
    	  HttpServletResponse resp=(HttpServletResponse)response;
    	  
    	  logger.info("Pre-authenticated entry point called. Rejecting access:"+req.getRequestURI());

          if (isAjax(req)) {
              
          	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
              
          } else {
          	
          	resp.sendRedirect("/login.action?err=401");              
          }

       //!!WebUtils.toHttp(response).sendRedirect(sso_server_url.toString());
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
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
   	
}
