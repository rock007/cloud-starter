package org.cloud.db.sys.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import org.cloud.db.sys.entity.Organization;

/**
 * Created by sam on 2017/7/7.
 */
public interface OrganizationRepository  extends CrudRepository<Organization, Long>,JpaSpecificationExecutor<Organization> {

	List<Organization> findByPid(Long pid);

}
