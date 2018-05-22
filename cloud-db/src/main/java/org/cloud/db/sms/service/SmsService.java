package org.cloud.db.sms.service;

import org.springframework.data.domain.Page;

import org.cloud.db.sms.entity.SmsReceiveLog;
import org.cloud.db.sms.entity.SmsReceiveResp;
import org.cloud.db.sms.entity.SmsSendLog;
import org.cloud.db.sms.entity.SmsTemplate;

public interface SmsService {

    public SmsTemplate saveTemplate(SmsTemplate m);

    public void deleteTemplate(long id);

    public SmsTemplate getTemplateById(long id);
    
    public SmsTemplate getTemplateByName(String name);

	public Page<SmsTemplate> searchTemplatesBy(SmsTemplate m,int page,int pageSize);
	
    public void saveSmsReceive(SmsReceiveLog m);

    public Page<SmsReceiveLog> searchReceivesBy(SmsReceiveLog m,int page,int pageSize,String beginDateStr,String endDateStr);
    
    public void saveSmsSend(SmsSendLog m);

    public Page<SmsSendLog> searchSendsBy(SmsSendLog m,int page,int pageSize,String beginDateStr,String endDateStr);
    
    public SmsReceiveResp saveReceiveResp(SmsReceiveResp m);

    public void deleteReceiveResp(long id);

    public SmsReceiveResp getReceiveRespById(long id);
    
    public Page<SmsReceiveResp> searchReceiveRespBy(SmsReceiveResp m,int page,int pageSize);
    
    public SmsSendLog getSendLogById(long id);
}
