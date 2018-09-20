package org.cloud.db.sys.service.imp;

import java.util.List;

import org.cloud.db.sys.entity.SysSystem;
import org.cloud.db.sys.repository.SystemRepository;
import org.cloud.db.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("systemService")
public class SystemServiceImp implements SystemService{

	@Autowired
	private SystemRepository systemRepository;
	
	@Override
	public void saveSystem(SysSystem m) {

		systemRepository.save(m);
	}

	@Override
	public List<SysSystem> getAll() {
		
		return Lists.newArrayList( systemRepository.findAll());
	}

	@Override
	public SysSystem getSystemById(long id) {

		return systemRepository.findOne(id);
	}
	
}
