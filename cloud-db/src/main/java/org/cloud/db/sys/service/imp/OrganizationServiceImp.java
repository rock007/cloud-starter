package org.cloud.db.sys.service.imp;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.cloud.db.sys.entity.Organization;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.UserOrganization;
import org.cloud.db.sys.repository.OrganizationRepository;
import org.cloud.db.sys.repository.UserOrganizationRepository;
import org.cloud.db.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("organizationService")
public class OrganizationServiceImp implements OrganizationService{

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private UserOrganizationRepository userOrganizationRepository;
	
	@Override
	public Organization save(Organization m) {
		
		return organizationRepository.save(m);
	}

	@Override
	public Organization get(Long id) {

		return organizationRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Organization> getByPid(Long pid) {
		
		return organizationRepository.findByPid(pid);
	}

	@Override
	public Page<Organization> search(Organization m, int page, int pageSize) {
		
		return organizationRepository.findAll(new Specification<Organization>() {
			public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Long pid = m.getPid(); 
				
				String name=m.getName();
				
				Predicate p1,p2,p3,p4,pc;
			    
			    p1 = cb.notEqual(root.get("name").as(String.class), "fuck-you");
				pc=cb.and(p1);
			    
			    if(!StringUtils.isEmpty(name)){
			    	
					p2 =cb.like(root.get("name").as(String.class),"%"+name.toLowerCase()+"%");
					pc=cb.and(pc,p2);
				}
			    
			    if(pid!=null){
			    	p4 =cb.equal(root.get("pid").as(Long.class),pid);
					pc=cb.and(pc,p4);
			    }
			    query.where(pc);
			    
				// 添加排序的功能
				query.orderBy(cb.desc(root.get("ctime").as(Long.class)),cb.asc(root.get("name").as(String.class)));
				
			    return null;
			}
		},  PageRequest.of(page, pageSize));
	}
	
	@Override
	public void delete(Long id) {
		organizationRepository.deleteById(id);		
	}

	@Override
	public UserOrganization saveUserOrganization(UserOrganization m) {
		
		return userOrganizationRepository.save(m);
	}

	@Override
	public UserOrganization getUserOrganization(Long id) {
		
		return userOrganizationRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteUserOrganization(Long id) {
		
		userOrganizationRepository.deleteById(id);
	}
	
	
	
}
