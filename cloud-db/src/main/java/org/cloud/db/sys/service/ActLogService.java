package org.cloud.db.sys.service;

import java.util.List;

import org.cloud.db.sys.entity.ActErrLog;
import org.springframework.data.domain.Page;


/**
 * 系统日志跟踪
 * @author 上午9:55:34 by sam
 *
 */
public interface ActLogService {

	public void saveLog(ActErrLog log);
	
	public List<ActErrLog> getActLogByOrder(long orderId);
	
	public Page<ActErrLog> search(ActErrLog m, int page, int pageSize);
}
