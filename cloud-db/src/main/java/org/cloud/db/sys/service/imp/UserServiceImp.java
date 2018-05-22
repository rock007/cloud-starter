package org.cloud.db.sys.service.imp;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.repository.PermissionRepository;
import org.cloud.db.sys.repository.UserPermissionRepository;
import org.cloud.db.sys.repository.UserRepository;
import org.cloud.db.sys.repository.UserRoleRepository;
import org.cloud.db.sys.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sam on 2017/7/10.
 */
@Component("userService")
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    public SysUser findUserByName(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public SysUser findUserById(Long user_id){
        return userRepository.findOne(user_id);
    }

    @Override
    public List<Permission> findPermissionsByUserId(Long userId){

        return permissionRepository.findByUserId(userId);
    }
}
