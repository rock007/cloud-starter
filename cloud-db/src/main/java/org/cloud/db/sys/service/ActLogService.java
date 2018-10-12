package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.ActLog;
import org.springframework.data.domain.Page;


/**
 * 系统日志跟踪
 * @author 上午9:55:34 by sam
 *
 */
public interface ActLogService {

	public void saveLog(ActLog log);
	
	public List<ActLog> getActLogByOrder(long orderId);
	
	public Page<ActLog> search(ActLog m, int page, int pageSize);
}
