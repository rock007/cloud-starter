package org.cloud.db.sms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.cloud.db.sms.entity.OrgGroup;
import org.cloud.db.sms.entity.OrgUser;

import java.util.List;

public interface OrgUserRepository extends CrudRepository<OrgUser, Long>,JpaSpecificationExecutor<OrgUser> {
	
	List<OrgUser> findByOrgId(Long orgId);

	
}