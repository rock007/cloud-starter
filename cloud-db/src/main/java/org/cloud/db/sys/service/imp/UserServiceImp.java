package org.cloud.db.sys.service.imp;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysRole;
import org.cloud.db.sys.entity.SysUser;
import org.cloud.db.sys.repository.PermissionRepository;
import org.cloud.db.sys.repository.RoleRepository;
import org.cloud.db.sys.repository.UserPermissionRepository;
import org.cloud.db.sys.repository.UserRepository;
import org.cloud.db.sys.repository.UserRoleRepository;
import org.cloud.db.sys.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by sam on 2017/7/10.
 */
@Component("userService")
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PermissionRepository permissionRepository;
    
    @Override
	public SysUser save(SysUser m) {

		return userRepository.save(m);
	}
    
	@Override
	public void delete(Long id) {

		userRepository.deleteById(id);
	}

	@Override
	public SysUser findUserByMobile(String mobile) {
		
		return userRepository.findByPhone(mobile);
	}

	@Override
    public SysUser findUserByName(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public SysUser findUserById(Long user_id){
        return userRepository.findById(user_id).orElse(null);
    }
   
    @Override
    public List<Permission> findPermissionsByUserId(Long systemId,Long userId){

        return permissionRepository.findByUserId(systemId,userId);
    }

	@Override
	public Page<SysUser> search(SysUser m, int page, int pageSize) {
	
		return userRepository.findAll(new Specification<SysUser>() {
			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				String username = m.getUsername();

				Integer locked = m.getLocked(); //状态
				
				String phone=m.getPhone();
				
				String realname=m.getRealname();
				
				Predicate p1,p2,p3,p4,p5,p6,pc;
			    
			    p1 = cb.notEqual(root.get("userId").as(Long.class), 0);
				pc=cb.and(p1);
			    
			    if(!StringUtils.isEmpty(username)){
			    	
					p2 =cb.like(root.get("name").as(String.class),"%"+username.toLowerCase()+"%");
					pc=cb.and(pc,p2);
				}
			    
			    if(!StringUtils.isEmpty(realname)){
			    	
					p3 =cb.like(root.get("realname").as(String.class),"%"+realname.toLowerCase()+"%");
					pc=cb.and(pc,p3);
				}
			    
			    if(locked!=null){
				    p4 =cb.equal(root.get("locked").as(Integer.class),locked);
					pc=cb.and(pc,p4);
			    }
			    
			    if(!StringUtils.isEmpty(phone)){
			    	p5 =cb.equal(root.get("phone").as(String.class),phone);
					pc=cb.and(pc,p5);
			    }
			    query.where(pc);
			    
				// 添加排序的功能
				query.orderBy(cb.desc(root.get("userId").as(Long.class)));
				
			    return null;
			}
		}, new PageRequest(page, pageSize));
	}

	@Override
	public List<SysRole> getRolesAll() {

		return Lists.newArrayList(roleRepository.findAll());
	}

	@Override
	public SysRole saveRole(SysRole m) {

		return roleRepository.save(m);
	}

	@Override
	public SysRole findRoleById(Long roleId) {
		
		return roleRepository.findById(roleId).orElse(null);
	}

	@Override
	public SysRole findRoleByName(String name) {
		
		List<SysRole> list= roleRepository.findByName(name);
		
		return list.size()>0?list.get(0):null;
	}

	@Override
	public void deleteRole(Long roleId) {

		roleRepository.deleteById(roleId);
	}
    
	
    
}
