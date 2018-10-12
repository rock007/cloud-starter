package org.cloud.db.sys.service.imp;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.cloud.db.sys.entity.ActLog;
import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.repository.ActLogRepository;
import org.cloud.db.sys.service.ActLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


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

	@Override
	public Page<ActLog> search(ActLog m, int page, int pageSize) {
		
		return actLogRepository.findAll(new Specification<ActLog>() {
			public Predicate toPredicate(Root<ActLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				String title = m.getTitle();

				String msg = m.getMsg(); 
				
				Predicate p1,p2,p3,p4,pc;
			    
			    p1 = cb.notEqual(root.get("id").as(Long.class), 0);
				pc=cb.and(p1);
			    
			    if(!StringUtils.isEmpty(title)){
			    	
					p2 =cb.like(root.get("title").as(String.class),"%"+title.toLowerCase()+"%");
					pc=cb.and(pc,p2);
				}
			    
			    if(!StringUtils.isEmpty(msg)){
			    	
					p3 =cb.like(root.get("msg").as(String.class),"%"+msg.toLowerCase()+"%");
					pc=cb.and(pc,p3);
				}
			    
			    query.where(pc);
			    
				// 添加排序的功能
				query.orderBy(cb.desc(root.get("id").as(Long.class)));
				
			    return null;
			}
		}, new PageRequest(page, pageSize));
	}

	
	
}
