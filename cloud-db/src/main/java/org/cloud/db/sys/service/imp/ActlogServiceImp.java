package org.cloud.db.sys.service.imp;

import java.util.List;

import org.cloud.db.sys.entity.ActLog;
import org.cloud.db.sys.repository.ActLogRepository;
import org.cloud.db.sys.service.ActLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("actLogService")
public class ActlogServiceImp implements ActLogService {

	@Autowired
	private ActLogRepository actLogRepository;
	
	@Override
	public void saveLog(ActLog log) {
		
		actLogRepository.save(log);
	}

	@Override
	public List<ActLog> getActLogByOrder(long orderId) {
	
		return null;		
		//return actLogRepository.findAll(ids);
	}

	
}
