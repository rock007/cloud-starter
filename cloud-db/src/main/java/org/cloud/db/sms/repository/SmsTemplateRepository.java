package org.cloud.db.sms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.cloud.db.sms.entity.SmsSendLog;
import org.cloud.db.sms.entity.SmsTemplate;

import java.util.List;

public interface SmsTemplateRepository extends CrudRepository<SmsTemplate, Long>,JpaSpecificationExecutor<SmsTemplate> {

    SmsTemplate findByName(String  name);

}