package org.cloud.db.sms.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.cloud.db.sms.entity.SmsReceiveLog;
import org.cloud.db.sms.entity.SmsSendLog;

import java.util.List;

public interface SmsReceiveLogRepository extends CrudRepository<SmsReceiveLog, Long>,JpaSpecificationExecutor<SmsReceiveLog> {

    List<SmsReceiveLog> findByStatus(Integer status);

}