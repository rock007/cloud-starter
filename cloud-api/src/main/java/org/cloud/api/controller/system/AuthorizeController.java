package org.cloud.api.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.cloud.api.shiro.jwt.JwtToken;
import org.cloud.api.shiro.jwt.TokenProvider;
import org.cloud.core.model.JsonBody;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value="/authorize")
public class AuthorizeController {

    @Autowired
    private TokenProvider tokenUtil;

	@Autowired
	private UserService userService;
	
	@ApiOperation(value="获得用户鉴权", notes="获得用户token")
    @PostMapping("/token")
    public @ResponseBody
    JsonBody<Map<String,Object>> token(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String,Object> result=new HashMap<>();
        String err="";
        // 验证用户名密码成功后生成token
        String token = tokenUtil.generateToken(username,password, "app");
        // 构建JwtToken
        JwtToken jwtToken = new JwtToken(username,password,token);

        Subject subject = SecurityUtils.getSubject();
        try {

            subject.login(jwtToken);
        } catch (UnknownAccountException exception) {
            exception.printStackTrace();
            err="账号不存在";
        } catch (IncorrectCredentialsException exception) {
            exception.printStackTrace();
            err="错误的凭证，用户名或密码不正确";
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
        if(subject.isAuthenticated()){

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
        	
        	return new JsonBody<Map<String,Object>>(-1,"获取token失败："+err,result);
           
        }
    }


    /**
     * 检查是否登录
     * @param token
     * @return
     */
	@ApiOperation(value="检查token是否合法")
    @GetMapping(value = "/check-token")
    public @ResponseBody  JsonBody<String> checkLogin(@CookieValue("token") String token){
        
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
    @GetMapping(value = "/logout")
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
        	//response.flushBuffer();
		}
		
		SecurityUtils.getSubject().logout();
		
        //session.removeAttribute(AppConst.CON_SESSION_USER_ROLE);
        //session.removeAttribute(AppConst.CON_SESSION_USER_NAME);
        
        return new JsonBody<String>(1,"success");
        
    }


    /**
     * 更新token
     * @param token
     * @return
     */
	@ApiOperation(value="更新token")
    @PostMapping("/refresh-token")
    public @ResponseBody  JsonBody<Map<String,Object>> refreshToken(@CookieValue(value = "token") String token) {
       
    	Map<String,Object> result=new HashMap<>();
    	
    	String newToken = tokenUtil.refreshToken(token);
        
        result.put("token", newToken);
        result.put("timestamp", Calendar.getInstance().getTimeInMillis());
        
        return new JsonBody<Map<String,Object>>(1,"success",result);
    }
}
