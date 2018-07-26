/**  
 * @Title ShiroAuthRealm.java
 * @date 2013-11-2 下午3:52:21
 * @Copyright: 2013 
 */
package org.cloud.backend.system.config.security;

import java.util.*;


import org.cloud.backend.system.dao.sys.model.SysPermission;
import org.cloud.backend.system.dao.sys.model.SysRole;
import org.cloud.backend.system.dao.sys.model.SysUser;
import org.cloud.backend.system.dao.sys.service.SysApiService;
import org.cloud.backend.system.dao.sys.service.SysUserRoleService;
import org.cloud.backend.system.dao.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cloud.core.utils.EncriptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * 
 * @author sam
 * @version 1.0
 */
public class ShiroAuthRealm extends AuthorizingRealm{

	@Value("${sso.type}")
	private String appType ="server";
	
	@Autowired
	private SysApiService sysApiService;

	/**
	 * 授权：验证权限时调用
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) principalCollection.getPrimaryPrincipal();
		SysUser upmsUser = sysApiService.selectSysUserByUsername(username);

		// 当前用户所有角色
		List<SysRole> upmsRoles = sysApiService.selectSysRoleBySysUserId(upmsUser.getUserId());
		Set<String> roles = new HashSet<>();
		for (SysRole upmsRole : upmsRoles) {
			if (StringUtils.isNotBlank(upmsRole.getName())) {
				roles.add(upmsRole.getName());
			}
		}

		// 当前用户所有权限
		List<SysPermission> upmsPermissions = sysApiService.selectSysPermissionBySysUserId(upmsUser.getUserId());
		Set<String> permissions = new HashSet<>();
		for (SysPermission upmsPermission : upmsPermissions) {
			if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
				permissions.add(upmsPermission.getPermissionValue());
			}
		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(permissions);
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
	}

	/**
	 * 认证：登录时调用
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();
		String password =authenticationToken.getCredentials()==null?"": new String((char[]) authenticationToken.getCredentials());

		// client无密认证
		if ("client".equals(appType)) {
			return new SimpleAuthenticationInfo(username, password, getName());
		}

		// 查询用户信息
		SysUser upmsUser = sysApiService.selectSysUserByUsername(username);

		if (null == upmsUser) {
			throw new UnknownAccountException();
		}
		//MD5Util.MD5(password + upmsUser.getSalt())
		if (!"".contains(password)&&!upmsUser.getPassword().equals(EncriptUtil.md5(password + upmsUser.getSalt()))) {
			throw new IncorrectCredentialsException();
		}
		if (upmsUser.getLocked() == 1) {
			throw new LockedAccountException();
		}

		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
