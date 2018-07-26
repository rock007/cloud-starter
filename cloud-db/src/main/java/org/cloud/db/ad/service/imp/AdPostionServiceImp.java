package org.cloud.db.ad.service.imp;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.cloud.db.ad.entity.AdPostion;
import org.cloud.db.ad.repository.AdPostionRepository;
import org.cloud.db.ad.service.AdPostionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author sam
 *
 */
@Component("adPostionService")
public class AdPostionServiceImp implements AdPostionService {

	@Autowired
	private AdPostionRepository adPostionRepository;
	
	@Override
	public AdPostion save(AdPostion m) {
		
		return adPostionRepository.save(m);
	}
	@Override
	public void delete(Long id){
		
		adPostionRepository.delete(id);
	}
	
	@Override
	public List<AdPostion> getByPostion(String postion) {
		
		return adPostionRepository.findByPostion(postion);
	}
	
	
	@Override
	public AdPostion findById(Long id){
		
		return adPostionRepository.findOne(id);
	}
	
	public Page<AdPostion> search( String  postionlike,int page,int pageSize){
		
		return adPostionRepository.findAll(new Specification<AdPostion>() {
			public Predicate toPredicate(Root<AdPostion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
			    Predicate p1,p2,pc;
			    
			    p1 = cb.notEqual(root.get("id").as(Long.class), 0);
				pc=cb.and(p1);
			    
			    if(!StringUtils.isEmpty(postionlike)){
			    	
					p2 =cb.like(root.get("postion").as(String.class),"%"+postionlike.toLowerCase()+"%");
					pc=cb.and(pc,p2);
				}
			   
			    query.where(pc);
			    
				// 添加排序的功能
				query.orderBy(cb.desc(root.get("create_date").as(Date.class)),cb.desc(root.get("update_date").as(Date.class)));
				
			    return null;
			}
		}, new PageRequest(page, pageSize));
	}
	
	public Page<AdPostion> search2( String  postionlike,int page,int pageSize){
		
		
		if(StringUtils.isEmpty(postionlike) ){
		
			return adPostionRepository.findAll( new PageRequest(page, pageSize));
		}
		
		return adPostionRepository.findByPostionLike("%"+postionlike+"%",  new PageRequest(page, pageSize));
	}
}
