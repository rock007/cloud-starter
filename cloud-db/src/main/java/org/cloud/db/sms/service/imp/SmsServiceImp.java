package org.cloud.db.sms.service.imp;

import org.cloud.db.sms.entity.SmsReceiveLog;
import org.cloud.db.sms.entity.SmsReceiveResp;
import org.cloud.db.sms.entity.SmsSendLog;
import org.cloud.db.sms.entity.SmsTemplate;
import org.cloud.db.sms.repository.SmsReceiveLogRepository;
import org.cloud.db.sms.repository.SmsReceiveRespRepository;
import org.cloud.db.sms.repository.SmsSendLogRepository;
import org.cloud.db.sms.repository.SmsTemplateRepository;
import org.cloud.db.sms.service.SmsService;

import java.util.Date;

import org.cloud.core.utils.DateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("SmsService")
public class SmsServiceImp implements SmsService {

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    @Autowired
    private SmsSendLogRepository smsSendLogRepository;

    @Autowired
    private SmsReceiveLogRepository smsReceiveLogRepository;
    
    @Autowired
    private SmsReceiveRespRepository smsReceiveRespRepository;

    @Override
    public SmsTemplate saveTemplate(SmsTemplate m) {

        return smsTemplateRepository.save(m);
    }

    @Override
    public void deleteTemplate(long id) {

        smsTemplateRepository.delete(id);
    }

    @Override
    public SmsTemplate getTemplateByName(String name) {
        return smsTemplateRepository.findByName(name);
    }
    
    @Override
    public SmsTemplate getTemplateById(long id){
    	return smsTemplateRepository.findOne(id);
    }
    
    @Override
	public Page<SmsTemplate> searchTemplatesBy(final SmsTemplate m,int page,int pageSize){
    	
    	return smsTemplateRepository.findAll(new Specification<SmsTemplate>() {

			public Predicate toPredicate(Root<SmsTemplate> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {


				String name = m.getName();
				
				Predicate p1,p2,p3,p4,pc;

				if (!StringUtils.isEmpty(name)) {
					p1 = cb.like(root.get("name").as(String.class), "%"
							+ name.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "0");
					pc=cb.and(p1);
				
				}
				
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.desc(root.get("id").as(Long.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}
    
    @Override
    public void saveSmsReceive(SmsReceiveLog m) {

        smsReceiveLogRepository.save(m);
    }

    @Override
    public void saveSmsSend(SmsSendLog m) {

        smsSendLogRepository.save(m);
    }

	@Override
	public Page<SmsReceiveLog> searchReceivesBy(SmsReceiveLog m, int page, int pageSize,String beginDateStr,String endDateStr) {
		
		return smsReceiveLogRepository.findAll(new Specification<SmsReceiveLog>() {

			public Predicate toPredicate(Root<SmsReceiveLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {


				String sendMobile = m.getSendMobile();
				
				String content = m.getContent();
				
				Integer status = m.getStatus();
				
				Predicate p1,p2,p3,p4,p5,pc;

				if (!StringUtils.isEmpty(sendMobile)) {
					p1 = cb.like(root.get("sendMobile").as(String.class), "%"
							+ sendMobile.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "0");
					pc=cb.and(p1);
				}
				
				if(!StringUtils.isEmpty(content)){

					p2 = cb.notEqual(root.get("content").as(String.class), content);
					pc=cb.and(p2);
				}
				
				if(status!=null){

					p3 = cb.equal(root.get("status").as(Integer.class), status);
					pc=cb.and(p3);
					
				}
				
				if(!StringUtils.isEmpty(beginDateStr)){
                	
                	p4 = cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class), DateUtil.getDate(beginDateStr+" 00:00:00",DateUtil.dtfull_sd));
					pc=cb.and(pc,p4);
					
                }
                if(!StringUtils.isEmpty(endDateStr)){
                	
                	p5 = cb.lessThan(root.get("createDate").as(Date.class), DateUtil.getDate(endDateStr+" 23:59:59",DateUtil.dtfull_sd));
					pc=cb.and(pc,p5);
                }
				
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.desc(root.get("id").as(Long.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}

	@Override
	public Page<SmsSendLog> searchSendsBy(SmsSendLog m, int page, int pageSize,String beginDateStr,String endDateStr) {
		return smsSendLogRepository.findAll(new Specification<SmsSendLog>() {

			public Predicate toPredicate(Root<SmsSendLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {


				String to_mobile = m.getTo_mobile();
				
				String content = m.getContent();
				
				Integer sendType = m.getSendType();
				
				Integer status = m.getStatus();
				
				Predicate p1,p2,p3,p4,p5,p6,pc;

				if (!StringUtils.isEmpty(to_mobile)) {
					p1 = cb.like(root.get("to_mobile").as(String.class), "%"
							+ to_mobile.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "0");
					pc=cb.and(p1);
				}
				
				if(!StringUtils.isEmpty(content)){

					p2 = cb.equal(root.get("content").as(String.class), content);
					pc=cb.and(pc,p2);
				}
				
				if(status!=null){

					p3 = cb.equal(root.get("status").as(Integer.class), status);
					pc=cb.and(pc,p3);
					
				}
				
				if(sendType!=null){

					p6 = cb.equal(root.get("sendType").as(Integer.class), sendType);
					pc=cb.and(pc,p6);
					
				}

				if(!StringUtils.isEmpty(beginDateStr)){
                	
                	p4 = cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class), DateUtil.getDate(beginDateStr+" 00:00:00",DateUtil.dtfull_sd));
					pc=cb.and(pc,p4);
					
                }
                if(!StringUtils.isEmpty(endDateStr)){
                	
                	p5 = cb.lessThan(root.get("createDate").as(Date.class), DateUtil.getDate(endDateStr+" 23:59:59",DateUtil.dtfull_sd));
					pc=cb.and(pc,p5);
                }
				
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.desc(root.get("id").as(Long.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}

	@Override
	public SmsReceiveResp saveReceiveResp(SmsReceiveResp m) {
	
		return smsReceiveRespRepository.save(m);
	}

	@Override
	public void deleteReceiveResp(long id) {
		
		smsReceiveRespRepository.delete(id);
	}

	@Override
	public SmsReceiveResp getReceiveRespById(long id) {
		
		return smsReceiveRespRepository.findOne(id);
	}

	@Override
	public Page<SmsReceiveResp> searchReceiveRespBy(SmsReceiveResp m, int page, int pageSize) {
		
		return smsReceiveRespRepository.findAll(new Specification<SmsReceiveResp>() {

			public Predicate toPredicate(Root<SmsReceiveResp> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {


				String regstr = m.getRegstr();
				
				String resp = m.getResp();
				
				Integer flag = m.getFlag();
				
				Predicate p1,p2,p3,p4,p5,pc;

				if (!StringUtils.isEmpty(resp)) {
					p1 = cb.like(root.get("resp").as(String.class), "%"
							+ resp.toLowerCase() + "%");
					
					pc=cb.and(p1);

				} else {
					p1 = cb.notEqual(root.get("id").as(Long.class), "0");
					pc=cb.and(p1);
				}
				
				if(!StringUtils.isEmpty(regstr)){

					p2 = cb.notEqual(root.get("regstr").as(String.class), regstr);
					pc=cb.and(p2);
				}
				
				if(flag!=null){

					p3 = cb.equal(root.get("flag").as(Integer.class), flag);
					pc=cb.and(p3);
					
				}
				
				query.where(pc);

				// 添加排序的功能
				query.orderBy(cb.desc(root.get("id").as(Long.class)));

				return null;
			}

		}, new PageRequest(page, pageSize));
	}
	
	@Override
    public SmsSendLog getSendLogById(long id){
    
		return smsSendLogRepository.findOne(id);
    }
    
    
}
