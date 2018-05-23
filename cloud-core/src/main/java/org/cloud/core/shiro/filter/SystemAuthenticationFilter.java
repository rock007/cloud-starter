package org.cloud.core.shiro.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.cloud.core.app.AppConst;
import org.cloud.core.utils.HttpUtil;
import org.cloud.core.utils.RequestParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.google.gson.Gson;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 重写authc过滤器
 */
public class SystemAuthenticationFilter extends AuthenticationFilter {
	
    private final static Logger _log = LoggerFactory.getLogger(SystemAuthenticationFilter.class);

	@Value("${sso.type}")
	private String ssoType ="server";
	
	@Value("${sso.server.url}")
	private String ssoServerUrl ="http://127.0.0.1:10000";
	
	@Value("${sso.appID}")
	private String ssoAppid ="";
	
	@Autowired
    private StringRedisTemplate  redisTemplate;
	
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        String curUrl = getRequestUrl(request);

		Subject subject = SecurityUtils.getSubject();
		
        if(curUrl.indexOf("/resources/")>=0
                ||curUrl.indexOf("/plugins/")>=0 ||curUrl.indexOf("/fonts/")>=0
                ||curUrl.indexOf("/js/")>=0||curUrl.indexOf("/css/")>=0
                ||curUrl.indexOf("/images/")>=0
                ||curUrl.indexOf("/sso/")>=0
                		||curUrl.indexOf("/demo/")>=0
                        		||curUrl.indexOf("_js_")>=0
                        		||curUrl.indexOf("/error")>=0
                                		||curUrl.indexOf("/403")>=0
                                        		||curUrl.indexOf("/info")>=0
                                        		||curUrl.endsWith(".json")
                        ||curUrl.endsWith("/")||curUrl.indexOf(".ico")>=0) {
            return true;
        }

        _log.info("getRequestURI ==" + curUrl);
        
        _log.debug("ssoType :" + ssoType+" ssoServerUrl:"+ssoServerUrl+" ssoAppid:"+ssoAppid);
        
        Session session = subject.getSession();

        // 判断请求类型
        session.setAttribute(AppConst.SSO_TYPE, ssoType);
        
        if ("server".equals(ssoType)) {
        	
            return subject.isAuthenticated();
            
        }else if ("client".equals(ssoType)) {
        	
            return validateClient(request, response);
        } 
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
      
    	String backurl = getRequestUrlWithHost(request);
    	
    	// server需要登录
        if ("server".equals(ssoType)) {
            WebUtils.toHttp(response).sendRedirect("/sso/login?backurl="+(URLEncoder.encode(backurl, "utf-8")));
            return false;
        }
        StringBuffer sso_server_url =new StringBuffer(ssoServerUrl);
        sso_server_url.append("/sso/index").append("?").append("appid").append("=").append(ssoAppid);
        
        // 回跳地址
        sso_server_url.append("&").append("backurl").append("=").append(URLEncoder.encode(backurl.toString(), "utf-8"));
        WebUtils.toHttp(response).sendRedirect(sso_server_url.toString());
        
        return false;
    }

    /**
     * 认证中心登录成功带回code
     * @param request
     */
    private boolean validateClient(ServletRequest request, ServletResponse response) {
       
    	HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
    	HttpServletRequest httpServletRequest=WebUtils.toHttp(request);
    			
    	Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        int timeOut = (int) session.getTimeout() / 1000;
        
        // 判断局部会话是否登录
        String cacheClientSession =redisTemplate.opsForValue().get(AppConst.CLIENT_SESSION_ID + "_" + sessionId);
        
        _log.info("sessionId:"+sessionId+" check session :"+cacheClientSession);

        if (StringUtils.isNotBlank(cacheClientSession)) {
            // 更新code有效期
            
            redisTemplate.opsForValue().set(AppConst.CLIENT_SESSION_ID + "_" + sessionId, cacheClientSession, timeOut, TimeUnit.SECONDS);
            
            redisTemplate.expire(AppConst.CLIENT_SESSION_IDS + "_" + cacheClientSession, timeOut, TimeUnit.SECONDS);
            
            // 移除url中的code参数
            if (null != request.getParameter("code")) {
                String backUrl = RequestParameterUtil.getParameterWithOutCode(httpServletRequest);
                
                _log.debug("backUrl 1:"+backUrl);
                
                try {
                    httpServletResponse.sendRedirect(backUrl.toString());
                    //httpServletResponse.flushBuffer();
                    
                    return true;
                    
                } catch (IOException e) {
                    _log.error("局部会话已登录，移除code参数跳转出错：", e);
                }
            } else {
                return true;
            }
        }
        // 判断是否有认证中心code
        String code = request.getParameter("code");
        _log.info(" check code :"+code);
        
        // 已拿到code
        if (StringUtils.isNotBlank(code)) {
            // HttpPost去校验code
        	 StringBuffer sso_server_url = new StringBuffer(ssoServerUrl);

             _log.info("sso_server_url :"+sso_server_url);

             String resp =HttpUtil.get(sso_server_url.toString() + "/sso/code?code="+code);
             _log.debug("服务器验证:"+sso_server_url+" code:"+code);
             //!!!24 上专有
             //String resp =HttpUtil.get("http://127.0.0.1:10004" + "/sso/code?code="+code);
             //_log.debug("服务器验证:http://127.0.0.1:10004"+" code:"+code);
             
             _log.info("check code resp:"+resp);
             
             if (resp!=null&&!"".equals(resp)) {
             	
            	 Gson g=new Gson();
            	 Map result = g.fromJson(resp, Map.class);
                 
                 if (1 == Integer.parseInt(result.get("result").toString()) && result.get("data").equals(code)) {
                    
                     // 移除url中的token参数
                     String backUrl = RequestParameterUtil.getParameterWithOutCode(httpServletRequest);

                     _log.debug("backUrl 2:"+backUrl);
                     // 返回请求资源
                     try {
                         
                    	 // client无密认证
                         String username = request.getParameter("username");
                         
                         _log.debug("client login "+username);
                         
                         subject.login(new UsernamePasswordToken(username, ssoAppid));
                         
                         _log.debug("before sendRedirect ");
                         // code校验正确，创建局部会话
                         
                         redisTemplate.opsForValue().set(AppConst.CLIENT_SESSION_ID + "_" + sessionId, code, timeOut, TimeUnit.SECONDS);
                         
                         redisTemplate.opsForList().leftPush(AppConst.CLIENT_SESSION_IDS + "_" + code, sessionId);
                         redisTemplate.expire(AppConst.CLIENT_SESSION_ID + "_" + code, timeOut, TimeUnit.SECONDS);
                         
                         httpServletResponse.sendRedirect(backUrl.toString());
                         
                         //option 2
                         //httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                         //httpServletResponse.setHeader("Location", backUrl.toString());
                     
                         //httpServletResponse.flushBuffer();
                         
                         _log.debug("after sendRedirect ");
                         
                         return true;
                     } catch (Exception e) {
                         _log.error("已拿到code，login 出错：", e);
                     }
                 } else {
                     _log.warn("check code error,resp data:"+result.get("data"));
                 }
             }else{
                 _log.warn("check code  , goto login ??");

             }
        }
       
        return false;
    }

    /***
     * get full path
     * @param request
     * @return
     */
    private String getRequestUrl(ServletRequest request) {

        HttpServletRequest req = WebUtils.toHttp(request);
        
        String queryString = req.getQueryString();
        queryString = StringUtils.isBlank(queryString)?"": "?"+queryString;
        
        String url= req.getRequestURL()+queryString;
        
        return url.replace(ssoServerUrl, "");
        
    }
    
    private String getRequestUrlWithHost(ServletRequest request) {
        
        HttpServletRequest req = WebUtils.toHttp(request);
        
        String queryString = req.getQueryString();
        queryString = StringUtils.isBlank(queryString)?"": "?"+queryString;
        
        return req.getRequestURL()+queryString;
        
    }
   
}
