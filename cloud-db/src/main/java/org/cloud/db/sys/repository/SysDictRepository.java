/**
*@Author: sam
*@Date: 2017年11月21日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.db.sys.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sys.entity.SysDic;

public interface SysDictRepository  extends CrudRepository<SysDic, Long> {

	List<SysDic> findByParentId(Long parentId);
}

