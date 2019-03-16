/**  
 * @Title ShiroAuthRealm.java
 * @date 2013-11-2 下午3:52:21
 * @Copyright: 2013 
 */
package org.cloud.shop.config.security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cloud.db.sys.entity.SysUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class ShiroAuthRealm extends AuthorizingRealm{

	private static final Logger logger = LoggerFactory.getLogger(ShiroAuthRealm.class);
	
	//@Autowired
	//private SysFeignService sysFeignService;

	/**
	 * 授权：验证权限时调用
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//String username = (String) principalCollection.getPrimaryPrincipal();

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		return simpleAuthorizationInfo;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException{
		
		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password= new String(upToken.getPassword());
        
		logger.debug("Auth,username"+username);
		
		logger.debug("Auth, password:" + password);

		// 查询用户信息
		SysUser upmsUser =null; //sysFeignService.findUserByName(username);

		if (null == upmsUser) {
			throw new UnknownAccountException();
		}
		// MD5Util.MD5(password + upmsUser.getSalt())
		if ("".contains(password) && !upmsUser.getPassword().equals(password)) {
			throw new IncorrectCredentialsException();
		}
		if (upmsUser.getLocked() == 1) {
			throw new LockedAccountException();
		}

		return new SimpleAuthenticationInfo(username, password, getName());
			
	}

}
