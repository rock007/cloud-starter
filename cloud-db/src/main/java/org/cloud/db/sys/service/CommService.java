/**
*@Author: sam
*@Date: 2017年11月21日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.SysDic;

public interface CommService {

	public void saveDict(SysDic m);
	
	public List<SysDic> getDictByParentId(long parentId);
	
	public SysDic getDictById(long id);
}

