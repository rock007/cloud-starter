package org.cloud.db.sms.repository;

import org.cloud.db.cms.entity.Article;
import org.cloud.db.sms.entity.OrgGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrgGroupRepository extends CrudRepository<OrgGroup, Long> {
	
	List<OrgGroup> findByParentId(Long parentId);

	
}