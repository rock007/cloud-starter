package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.Organization;
import org.cloud.db.sys.entity.UserOrganization;
import org.springframework.data.domain.Page;

public interface OrganizationService {

	public Organization save(Organization m);
	
	public Organization get(Long id);
	
	public List<Organization> getByPid(Long pid);
	
	public Page<Organization> search(Organization m, int page, int pageSize);
	
	public void delete(Long id);
	
	public UserOrganization saveUserOrganization(UserOrganization m);
	
	public UserOrganization getUserOrganization(Long id);
	
	public void deleteUserOrganization(Long id);
	
}
