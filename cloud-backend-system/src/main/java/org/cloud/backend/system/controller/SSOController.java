package org.cloud.backend.system.controller;

import org.cloud.backend.system.comm.constant.Result;
import org.cloud.backend.system.comm.constant.ResultConstant;

import org.cloud.backend.system.dao.sys.model.SysSystemExample;
import org.cloud.backend.system.dao.sys.service.SysSystemService;
import org.cloud.core.app.AppConst;
import org.cloud.core.shiro.session.SystemSessionDao;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 单点登录管理
 * sam is here  2016/12/10.
 */
@Controller
@RequestMapping("/sso")
public class SSOController extends BaseController {

    private final static Logger _log = LoggerFactory.getLogger(SSOController.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    SysSystemService sysSystemService;

    @Autowired
    SystemSessionDao systemSessionDao;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) throws Exception {
        String appid = request.getParameter("appid");
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("无效访问！");
        }
        // 判断请求认证系统是否注册
        SysSystemExample sysSystemExample = new SysSystemExample();
        sysSystemExample.createCriteria()
                .andNameEqualTo(appid);
        int count = sysSystemService.countByExample(sysSystemExample);
        if (0 == count) {
            throw new RuntimeException(String.format("未注册的系统:%s", appid));
        }
        return "redirect:/sso/login?backurl=" + URLEncoder.encode(backurl, "utf-8");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳
        //String code =RedisUtil.get(AppConst.SERVER_SESSION_ID + "_" + serverSessionId);
        String code=stringRedisTemplate.opsForValue().get(AppConst.SERVER_SESSION_ID + "_" + serverSessionId);
        
        // code校验值
        if (StringUtils.isNotBlank(code)) {
            // 回跳
            String backurl = request.getParameter("backurl");
            String username = (String) subject.getPrincipal();
            if (StringUtils.isBlank(backurl)) {
                backurl = "/";
            } else {
                if (backurl.contains("?")) {
                    backurl += "&code=" + code + "&username=" + username;
                } else {
                    backurl += "?code=" + code + "&username=" + username;
                }
            }
            _log.debug("认证中心帐号通过，带code回跳：{}", backurl);
            return "redirect:" + backurl;
        }
        return "/sso/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        if (StringUtils.isBlank(username)) {
            return new Result(ResultConstant.EMPTY_USERNAME, "帐号不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return new Result(ResultConstant.EMPTY_PASSWORD, "密码不能为空！");
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
                return new Result(ResultConstant.INVALID_USERNAME, "帐号不存在！");
            } catch (IncorrectCredentialsException e) {
                return new Result(ResultConstant.INVALID_PASSWORD, "密码错误！");
            } catch (LockedAccountException e) {
                return new Result(ResultConstant.INVALID_ACCOUNT, "帐号已锁定！");
            }
            String code = UUID.randomUUID().toString();
            
            // 全局会话sessionId列表，供会话管理
            stringRedisTemplate.opsForList().leftPush(AppConst.SERVER_SESSION_IDS, sessionId);
            // 全局会话的code
            stringRedisTemplate.opsForValue().set(AppConst.SERVER_SESSION_ID + "_" + sessionId, code, session.getTimeout(), TimeUnit.MILLISECONDS);
            // code校验值
            stringRedisTemplate.opsForValue().set(AppConst.SERVER_CODE + "_" + code, code, session.getTimeout(), TimeUnit.MILLISECONDS);
          
        }
        // 回跳登录前地址
        /**
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(backurl)) {
            return new Result(ResultConstant.SUCCESS, "/manage/index");
        } else {
            return new Result(ResultConstant.SUCCESS, backurl);
        }
        **/
        return new Result(ResultConstant.SUCCESS, "/manage/index");
    }

    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseBody
    public Object code(HttpServletRequest request) {
        String codeParam = request.getParameter("code");
        String code=stringRedisTemplate.opsForValue().get(AppConst.SERVER_CODE + "_" + codeParam);
        
        if (StringUtils.isBlank(codeParam) || !codeParam.equals(code)) {
            new Result(ResultConstant.FAILED, "无效code");
        }
        return new Result(ResultConstant.SUCCESS, code);
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

}