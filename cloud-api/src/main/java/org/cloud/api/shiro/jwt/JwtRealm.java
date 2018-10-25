package org.cloud.api.shiro.jwt;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cloud.core.utils.EncriptUtil;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysRole;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private TokenProvider tokenUtil;

	@Autowired
	private UserService userService;
	
    @Override
    public boolean supports(AuthenticationToken token) {
        //表示此Realm只支持JwtToken类型
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
       
        String username = (String) principals.getPrimaryPrincipal();
		SysUser curUser = userService.findUserByName(username);
		
		// 当前用户所有角色
		Set<SysRole> upmsRoles =curUser.getRoles();
		Set<String> roles = new HashSet<>();
		for (SysRole upmsRole : upmsRoles) {
			if (StringUtils.isNotBlank(upmsRole.getName())) {
				roles.add(upmsRole.getName());
			}
		}

		// 当前用户所有权限
		List<Permission> userPermissions = userService.findPermissionsByUserId(1L,curUser.getUserId());
		Set<String> permissions = new HashSet<>();
		for (Permission p : userPermissions) {
			if (StringUtils.isNotBlank(p.getPermission_value())) {
				permissions.add(p.getPermission_value());
			}
		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(permissions);
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;

        // 获取token
        String token = jwtToken.getToken();

        if(token==null||"".equals(token)){
        	
        	throw new IncorrectCredentialsException();
        }
        
        // 从token中获取用户名
        String username = tokenUtil.getUsernameFromToken(token);
        String password=tokenUtil.getPasswordFromToken(token);
        // 根据用户名查询数据库
        SysUser loginUser = userService.findUserByName(username);

        // 用户不存在
        if (loginUser == null) {
            throw new UnknownAccountException();
        }
      
        
		if (!StringUtils.isEmpty(password)&&!loginUser.getPassword().equals(EncriptUtil.md5(password + loginUser.getSalt()))) {
			throw new IncorrectCredentialsException();
		}
        // 用户被禁用
        if(loginUser.getLocked()==1){
            throw new LockedAccountException();
        }

        try {
            return new SimpleAuthenticationInfo(
                    username,
                    token,
                    getName()
            );
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
