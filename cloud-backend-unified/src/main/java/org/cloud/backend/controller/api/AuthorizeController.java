package org.cloud.backend.controller.api;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.cloud.core.base.JsonBaseController;
import org.cloud.core.model.JsonBody;
import org.cloud.core.shiro.jwt.JwtToken;
import org.cloud.core.shiro.jwt.TokenProvider;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value="/api/")
public class AuthorizeController extends JsonBaseController{

    @Autowired
    private TokenProvider tokenUtil;

	@Autowired
	private UserService userService;
	
	@ApiOperation(value="获得用户鉴权", notes="获得用户token")
    @PostMapping("/token.json")
    public @ResponseBody  JsonBody<Map<String,Object>> token(String username,String password, HttpServletRequest request, HttpServletResponse response) throws IOException {

		if(StringUtils.isEmpty(username)){
	            return new JsonBody<>(-1,"用户名 不能为空");
	    }
		if(StringUtils.isEmpty(password)){
            return new JsonBody<>(-1,"密码 不能为空");
		}
		
        Map<String,Object> result=new HashMap<>();
        String err="";
        // 验证用户名密码成功后生成token
        String token = tokenUtil.generateToken(username,password, "mobile");
        // 构建JwtToken
        JwtToken jwtToken = new JwtToken(username,password,token);

        Subject subject = SecurityUtils.getSubject();
        //SysUser curUser=null;
        //int relogin=0;
        try {
        	/**
        	curUser= userService.findUserByName(username);
        	
        	if(curUser!=null&&curUser.getRelogin()!=null&&curUser.getRelogin()==1){
        		
        		relogin=1;
        		curUser.setRelogin(0);
        		
        		userService.save(curUser);
        	}
        	***/
            subject.login(jwtToken);
        } catch (UnknownAccountException exception) {
            exception.printStackTrace();
            err="账号不存在";
        } catch (IncorrectCredentialsException exception) {
            exception.printStackTrace();
            err="用户名或密码不正确";
        } catch (LockedAccountException exception) {
            exception.printStackTrace();
            err="账户已锁定";
        } catch (ExcessiveAttemptsException exception) {
            exception.printStackTrace();
            err="错误次数过多";
        } catch (AuthenticationException exception) {
            exception.printStackTrace();
            err="认证失败";
        }

        // 认证通过
        if(err.equals("")&&subject.isAuthenticated()){
        	
            // 将token写出到cookie
            Cookie cookie =new Cookie("token",token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600 * 5);
            cookie.setPath("/");
            response.addCookie(cookie);
            
            result.put("token", token);
            result.put("timestamp", Calendar.getInstance().getTimeInMillis());
          
            return new JsonBody<Map<String,Object>>(1,"success"+"",result);
            
        }else{
        	/***
        	if(curUser!=null&&relogin==1){
        		
        		curUser.setRelogin(1);
        		userService.save(curUser);
            		
        	}
        	***/
        	return new JsonBody<Map<String,Object>>(-1,err,result);
           
        }
    }


    /**
     * 检查是否登录
     * @param token
     * @return
     */
	@ApiOperation(value="检查token是否合法")
    @GetMapping(value = "/check-token.json")
    public @ResponseBody  JsonBody<String> checkLogin( String token){
        
        if(StringUtils.isEmpty(token)){
            return new JsonBody<String>(-1,"token为空");
        }

        // 从token中获取用户名
        String username = tokenUtil.getUsernameFromToken(token);

        if(StringUtils.isEmpty(username)){
            return new JsonBody<String>(-1,"无效token");
        }
        
        // 根据用户名查询数据库
        SysUser loginUser = userService.findUserByName(username);
        if(loginUser==null){
            return new JsonBody<String>(-1,"用户不存在");
        }
        
        return new JsonBody<String>(1,"token有效");
    }


    /**
     * 登出
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
	@ApiOperation(value="登出")
    @GetMapping(value = "/logout.json")
    public @ResponseBody  JsonBody<String> logout(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
        
		Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(ck -> "token".equals(ck.getName()))
                .limit(1)
                .map(ck -> {
                    ck.setMaxAge(1);
                    ck.setHttpOnly(true);
                    ck.setPath("/");
                    return ck;
                })
                .findFirst();
		
		if(cookie.get()!=null){
			response.addCookie(cookie.get());
		}
		
		SecurityUtils.getSubject().logout();
		
        return new JsonBody<String>(1,"success");
        
    }


    /**
     * 更新token
     * @param token
     * @return
     */
	@ApiOperation(value="更新token")
    @PostMapping("/refresh-token.json")
    public @ResponseBody  JsonBody<Map<String,Object>> refreshToken(String token) {
       
    	Map<String,Object> result=new HashMap<>();
    	
    	String newToken = tokenUtil.refreshToken(token);
        
        result.put("token", newToken);
        result.put("timestamp", Calendar.getInstance().getTimeInMillis());
        
        return new JsonBody<Map<String,Object>>(1,"success",result);
    }
}
