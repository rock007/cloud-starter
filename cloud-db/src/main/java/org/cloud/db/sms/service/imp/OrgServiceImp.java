package org.cloud.db.sms.service.imp;

import org.cloud.db.sms.entity.OrgGroup;
import org.cloud.db.sms.entity.OrgUser;
import org.cloud.db.sms.entity.SmsTemplate;
import org.cloud.db.sms.repository.OrgGroupRepository;
import org.cloud.db.sms.repository.OrgUserRepository;
import org.cloud.db.sms.service.OrgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component("orgService")
public class OrgServiceImp implements OrgService {

    @Autowired
    private OrgGroupRepository orgGroupRepository;

    @Autowired
    private OrgUserRepository orgUserRepository;

    @Override
    public OrgGroup saveGroup(OrgGroup m) {

        return orgGroupRepository.save(m);
    }

    @Override
    public void deleteGroup(long id) {
        orgGroupRepository.deleteById(id);
    }

    @Override
    public List<OrgGroup> getGroupsByParentId(long parentId) {
        return orgGroupRepository.findByParentId(parentId);
    }

    @Override
    public void saveUser(OrgUser m) {
        orgUserRepository.save(m);
    }

    @Override
    public void deleteUser(long id) {
        orgUserRepository.deleteById(id);
    }

    @Override
    public List<OrgUser> getUsersByOrgId(long orgId) {

        return orgUserRepository.findByOrgId(orgId);
    }

	@Override
	public OrgGroup getGroupById(long id) {
	
		return orgGroupRepository.findById(id).orElse(null);
	}

	@Override
	public OrgUser getUserById(long userId) {
		
		return orgUserRepository.findById(userId).orElse(null);
	}
    
	 @Override
		public Page<OrgUser> searchUsersBy(final OrgUser m,List<Long> orgids,int page,int pageSize){
	    	
	    	return orgUserRepository.findAll(new Specification<OrgUser>() {

				public Predicate toPredicate(Root<OrgUser> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {


					String name = m.getName();
					//Integer orgId=m.getOrgId();
					
					Predicate p1,p2,p3,p4,pc;

					if (!StringUtils.isEmpty(name)) {
						p1 = cb.like(root.get("name").as(String.class), "%"
								+ name.toLowerCase() + "%");
						
						pc=cb.and(p1);

					} else {
						p1 = cb.greaterThan(root.get("id").as(Long.class), 0L);
						pc=cb.and(p1);
					}
					
					if(orgids!=null&&orgids.size()>0){

						p2 = root.get("orgId").as(Long.class).in(orgids);
						pc=cb.and(pc,p2);
					}
					
					query.where(pc);

					// 添加排序的功能
					query.orderBy(cb.desc(root.get("id").as(Long.class)));

					return null;
				}

			}, new PageRequest(page, pageSize));
		}
    
}
