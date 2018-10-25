package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.SysSystem;

public interface SystemService {

	public void saveSystem(SysSystem m);
	
	public void deleteSystem(Long systemId);
	
	public List<SysSystem> getAll();
	
	public SysSystem getSystemById(long id);
	
	public SysSystem getSystemByName(String name);
	
	public SysSystem getSystemBySecDomain(String secdomain);
	
}
