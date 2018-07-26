package org.cloud.backend.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.cloud.core.app.AppConst;
import org.cloud.core.base.BaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.core.shiro.session.SystemSessionDao;
import org.cloud.db.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 单点登录管理
 * sam is here  2016/12/10.
 */
@Controller
@RequestMapping("/sso")
public class SSOController extends BaseController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    private final static Logger logger = LoggerFactory.getLogger(SSOController.class);
    // 全局会话key

    @Autowired
    UserService sysUserService;

    @Autowired
    SystemSessionDao systemSessionDao;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) throws Exception {
        String appid = request.getParameter("appid");
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("无效访问！");
        }
        
        return "redirect:/sso/login?backurl=" + URLEncoder.encode(backurl, "utf-8");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model,HttpServletRequest request) throws Exception {

        String backurl = request.getParameter("backurl");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳
        //String code =RedisUtil.get(AppConst.SERVER_SESSION_ID + "_" + serverSessionId);
        String code=stringRedisTemplate.opsForValue().get(AppConst.SERVER_SESSION_ID + "_" + serverSessionId);
        
        // code校验值
        if (StringUtils.isNotBlank(code)) {
            // 回跳

            String username = (String) subject.getPrincipal();
            if (StringUtils.isBlank(backurl)) {
                backurl = "/";
            } else {
            	
            	if (backurl.contains("code=")){
            		
            		throw new Exception("code redirect error,wrong backurl:"+backurl);
            	}
            	
                if (backurl.contains("?")) {
                    backurl += "&code=" + code + "&username=" + username;
                } else {
                    backurl += "?code=" + code + "&username=" + username;
                }
            }
            logger.debug("认证中心帐号通过，带code回跳：{}", backurl);
            return "redirect:" + backurl;
        }
        //加自动登录
        String uid = request.getParameter("uid");
        String appid=request.getParameter("appid");	
        
        logger.info(" uid :"+uid+",appID:"+appid);
        // 已拿到code
        if (StringUtils.isNotBlank(uid)) {
        	
        	return "redirect:" + "/sso/auto-login?uid="+uid+"&appid="+appid;
        	
        }
        
        model.addAttribute("backurl",backurl);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response,
                        Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        if (StringUtils.isBlank(username)) {
            model.addAttribute("msg","帐号不能为空！");
            return "login";
        }
        if (StringUtils.isBlank(password)) {
            model.addAttribute("msg","密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        //String hasCode = RedisUtil.get(AppConst.SERVER_SESSION_ID + "_" + sessionId);
        String hasCode=stringRedisTemplate.opsForValue().get(AppConst.SERVER_SESSION_ID + "_" + sessionId);
        
        // code校验值
        if (StringUtils.isBlank(hasCode)) {

            // 使用shiro认证
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                if (BooleanUtils.toBoolean(rememberMe)) {
                    usernamePasswordToken.setRememberMe(true);
                } else {
                    usernamePasswordToken.setRememberMe(false);
                }
                subject.login(usernamePasswordToken);

            } catch (UnknownAccountException e) {
                model.addAttribute("msg","帐号不存在！");
                return "login";
            } catch (IncorrectCredentialsException e) {

                model.addAttribute("msg","密码错误！");
                return "login";
            } catch (LockedAccountException e) {
                model.addAttribute("msg","帐号已锁定！");
                return "login";
            } catch (AuthenticationException e) {
                model.addAttribute("msg","登陆失败！");
                return "login";
            }
            
            // 默认验证帐号密码正确，创建code
            String code = UUID.randomUUID().toString();
            
            // 更新session状态
            //！！systemSessionDao.updateStatus(sessionId, SystemSession.OnlineStatus.on_line);
            
            // 全局会话sessionId列表，供会话管理
            stringRedisTemplate.opsForList().leftPush(AppConst.SERVER_SESSION_IDS, sessionId);
            // 全局会话的code
            stringRedisTemplate.opsForValue().set(AppConst.SERVER_SESSION_ID + "_" + sessionId, code, session.getTimeout(), TimeUnit.MILLISECONDS);
            // code校验值
            stringRedisTemplate.opsForValue().set(AppConst.SERVER_CODE + "_" + code, code, session.getTimeout(), TimeUnit.MILLISECONDS);

        }
        // 回跳登录前地址
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(backurl)||backurl.equals("/")) {
            return "redirect:/dashboard/";
        } else {
            return "redirect:" + backurl;
        }
    }


    @RequestMapping(value = "/code")
    public @ResponseBody JsonBody<String> code(HttpServletRequest request) {
        String codeParam = request.getParameter("code");
        //String code = RedisUtil.get(AppConst.SERVER_CODE + "_" + codeParam);
        String code=stringRedisTemplate.opsForValue().get(AppConst.SERVER_CODE + "_" + codeParam);
        
        if (StringUtils.isBlank(codeParam) || !codeParam.equals(code)) {
           return  new JsonBody<>(-1, "无效code","");
        }
        return new JsonBody<>(1, "ok",code);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        return "redirect:" + redirectUrl;
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Map<String, Object> model){

        return "unauthorized";
    }
 
    /**
     * 自动登录
     * @param model
     * @param userid
     * @param appid
     * @param req
     * @param rsp
     * @return
     */
    @RequestMapping(value="auto-login")
	public String auto_login(Model model,String userid,String appid,HttpServletRequest req,HttpServletResponse rsp){
	
        Subject subject = SecurityUtils.getSubject();
	try {
			
	        Session session = subject.getSession();
	        String sessionId = session.getId().toString();
	        
	        // 判断是否已登录，如果已登录，则回跳，防止重复登录
	        //String hasCode = RedisUtil.get(AppConst.SERVER_SESSION_ID + "_" + sessionId);
	        //Object oo=redisTemplate.boundValueOps(AppConst.SERVER_SESSION_ID + "_" + sessionId).get();
	        String hasCode=stringRedisTemplate.opsForValue().get(AppConst.SERVER_SESSION_ID + "_" + sessionId);
	        
	        if (StringUtils.isBlank(hasCode)) {
	        	
	        	UsernamePasswordToken token = new UsernamePasswordToken(userid,appid);
	        	
	        	//token.setRememberMe(false);
	        	 
	        	subject.login(token);
				model.addAttribute("status", 200);
				model.addAttribute("msg", "登录成功");	
			
				// 默认验证帐号密码正确，创建code
				String code = UUID.randomUUID().toString();
				
				  // 更新session状态
	            //!!!systemSessionDao.updateStatus(sessionId, SystemSession.OnlineStatus.on_line);
	            // 全局会话sessionId列表，供会话管理
				stringRedisTemplate.opsForList().leftPush(AppConst.SERVER_SESSION_IDS, sessionId);
	         
	            // 全局会话的code
				stringRedisTemplate.opsForValue().set(AppConst.SERVER_SESSION_ID + "_" + sessionId, code, session.getTimeout(), TimeUnit.MILLISECONDS);
	            // code校验值
				stringRedisTemplate.opsForValue().set(AppConst.SERVER_CODE + "_" + code, code, session.getTimeout(), TimeUnit.MILLISECONDS);
	            
	        }
		
			return "redirect:/dashboard/?p="+appid;
			
		} catch (UnknownAccountException e) {

			logger.error("auto_login UnknownAccountException", e);
			model.addAttribute("msg", "账号不存在");
			
		} catch (IncorrectCredentialsException e) {
			logger.error("auto_login IncorrectCredentialsException", e);
			model.addAttribute("msg", " 密码不正确");
		
		} catch (Exception e) {
			
			logger.error("auto_login Exception", e);
			
			model.addAttribute("status", 500);
			model.addAttribute("msg", e.getMessage());
		}
		return "login";
	}

    @RequestMapping(value="check-user-test")
    public @ResponseBody Map<String,String>    valid_demo(){
    	Map<String,String> result=new HashMap<>();
    	
    	result.put("isSuccessful", "true");
    	result.put("message", "成功");
    	result.put("result", "");
    	
    	return result;
    }
}