/**  
 * @Title ShiroAuthRealm.java
 * @date 2013-11-2 下午3:52:21
 * @Copyright: 2013 
 */
package org.cloud.backend.config.security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.service.UserService;
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
	
	@Autowired
	private UserService userService;

	@Autowired
	protected RestTemplate restTemplate;
	
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
        
		//String username = (String) authenticationToken.getPrincipal();
		
		logger.debug("Auth,username"+username);
		
		//String password = (String) authenticationToken.getCredentials();
		//String password =authenticationToken.getCredentials()==null?"": new String((char[]) authenticationToken.getCredentials());
		
		logger.debug("Auth, password:"+password);
			
			// 查询用户信息
			SysUser upmsUser = userService.findUserByName(username);

			if (null == upmsUser) {
				throw new UnknownAccountException();
			}
			//MD5Util.MD5(password + upmsUser.getSalt())
			if ("".contains(password)&&!upmsUser.getPassword().equals(password )) {
				throw new IncorrectCredentialsException();
			}
			if (upmsUser.getLocked() == 1) {
				throw new LockedAccountException();
			}

			return new SimpleAuthenticationInfo(username, password, getName());
		
			
	}

	private Map<String,Object>  getUserInfo(String userid,String idcode,String url){
		
		Map<String,Object> result=new HashMap<>();
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    map.add("userid", userid);
	    map.add("idcode", idcode);

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

	    try{
	    	
	    	logger.debug("get userInfo, serid:"+userid+" idcode:"+idcode);
	    	
	    	ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
	    	
	    	String jsonStr=response.getBody();
	    	
			logger.debug("get userInfo :"+jsonStr);
			
	    	Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(jsonStr, JsonObject.class);
			
			result.put("isSuccessful", jsonObject.get("isSuccessful").getAsString().toLowerCase());
			result.put("message", jsonObject.get("message").getAsString());
			//result.put("data", jsonObject.get("result").isJsonObject()?jsonObject.get("result").getAsJsonObject():jsonObject.get("result").getAsString());
			
	    }catch(Exception ex){
	    	
	    	logger.error("getUserInfo:", ex);
	    }
	    
	    return result;
	}

}
