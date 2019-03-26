/**
*@Author: sam
*@Date: 2017年11月21日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.db.sys.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.cloud.db.sys.entity.SysDic;
import org.cloud.db.sys.repository.SysDictRepository;
import org.cloud.db.sys.service.CommService;

@Component("commService")
public class CommServiceImp implements CommService{
	
	@Autowired
	private SysDictRepository sysDictRepository;
	
	@Override
	public void saveDict(SysDic m) {

		sysDictRepository.save(m);
	}

	@Override
	public List<SysDic> getDictByParentId(long parentId) {
		
		return sysDictRepository.findByParentId(parentId);
	}
	
	@Override
	public SysDic getDictById(long id){
		
		return sysDictRepository.findById(id).orElse(null);
	}
}

