package org.cloud.db.service.imp;

import java.util.List;

import org.cloud.db.entity.ActLog;
import org.cloud.db.repository.ActLogRepository;
import org.cloud.db.service.ActLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("actLogService")
public class ActlogServiceImp implements ActLogService{

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
