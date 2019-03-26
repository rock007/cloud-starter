/**
 * red-db  by sam @2018年7月9日  
 */
package org.cloud.db.sys.service.imp;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.RolePermission;
import org.cloud.db.sys.entity.UserPermission;
import org.cloud.db.sys.repository.PermissionRepository;
import org.cloud.db.sys.repository.RolePermissionRepository;
import org.cloud.db.sys.repository.UserPermissionRepository;
import org.cloud.db.sys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author sam
 *
 */
@Component("permissionService")
public class PermissionServiceImp implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private UserPermissionRepository userPermissionRepository;
	
	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public void delete(Long id) {

		userPermissionRepository.deleteByPermissionId(id);
		
		rolePermissionRepository.deleteByPermissionId(id);
		
		permissionRepository.deleteById(id);
	}

	@Override
	public Permission save(Permission w) {

		return permissionRepository.save(w);
	}

	@Override
	public Permission findById(Long id) {
		return permissionRepository.findById(id).orElse(null);
	}

	@Override
	public Page<Permission> search(Permission m, int page, int pageSize) {
		
		return permissionRepository.findAll(new Specification<Permission>() {
			

			/**
			 * 
			 */
			private static final long serialVersionUID = 7462403267832228994L;

			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				String name = m.getName();

				Integer status = m.getStatus(); //状态
				
				Long pid=m.getPid();
				
				Integer type=m.getType();
				
				Long systemId=m.getSystemId();
				
				Predicate p1,p2,p3,p4,p5,p6,pc;
			    
			    p1 = cb.notEqual(root.get("permissionId").as(Long.class), 0);
				pc=cb.and(p1);
			    
			    if(!StringUtils.isEmpty(name)){
			    	
					p2 =cb.like(root.get("name").as(String.class),"%"+name.toLowerCase()+"%");
					pc=cb.and(pc,p2);
				}
			    
			    if(status!=null){
				    p3 =cb.equal(root.get("status").as(Integer.class),status);
					pc=cb.and(pc,p3);
			    }
			    
			    if(pid!=null){
			    	p4 =cb.equal(root.get("pid").as(Long.class),pid);
					pc=cb.and(pc,p4);
			    }
			    
			    if(type!=null){
			    	p5 =cb.equal(root.get("type").as(Integer.class),type);
					pc=cb.and(pc,p5);
			    }
			    
			    if(systemId!=null){
			    	p6 =cb.equal(root.get("systemId").as(Long.class),systemId);
					pc=cb.and(pc,p6);
			    }
			    query.where(pc);
			    
				// 添加排序的功能
				query.orderBy(cb.desc(root.get("orders").as(Integer.class)),cb.asc(root.get("name").as(String.class)));
				
			    return null;
			}
		},  PageRequest.of(page, pageSize));
	}
	
	@Override
	public List<Permission> findBySystemIdAndPidAndType(Long systemId, Long pid, Integer mtype) {
		
		return permissionRepository.findBySystemIdAndPidAndType(systemId,pid, mtype);
	}

	@Override
	public void deleteRolePermission(Long id) {
		
		rolePermissionRepository.deleteById(id);
	}

	@Override
	public RolePermission saveRolePermission(RolePermission w) {
		
		return rolePermissionRepository.save(w);
	}

	@Override
	public RolePermission findRolePermissionById(Long id) {
		
		return rolePermissionRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<RolePermission> findRolePermissionByRoldId(Long roldId) {
		
		return rolePermissionRepository.findRolePermissionByRoleId(roldId);
	}	
	
	@Override
	public RolePermission findRolePermissionByRoleIdAndPermissionId(Long roldId,Long permissionId) {
		
		return rolePermissionRepository.findRolePermissionByRoleIdAndPermissionId(roldId,permissionId);
	}

	@Override
	public void deleteUserPermission(Long id) {
	
		rolePermissionRepository.deleteById(id);
	}

	@Override
	public UserPermission saveUserPermission(UserPermission w) {
		
		return userPermissionRepository.save(w);
	}

	@Override
	public UserPermission findUserPermissionById(Long id) {
	
		return userPermissionRepository.findById(id).orElse(null);
	}

	@Override
	public List<UserPermission> findUserPermissionByUserId(Long userId) {
		
		return userPermissionRepository.findByUserId(userId);
	}
	
	@Override
	public List<Permission> find4Menu(Long systemId,Long userId, Long pid) {
		
		return permissionRepository.find4Menu(systemId,userId, pid);
	}
	
	
	
}
