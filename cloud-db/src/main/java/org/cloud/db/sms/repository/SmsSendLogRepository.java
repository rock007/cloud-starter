package org.cloud.db.sms.repository;

import org.cloud.db.sms.entity.SmsSendLog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface SmsSendLogRepository extends CrudRepository<SmsSendLog, Long>,JpaSpecificationExecutor<SmsSendLog> {

    List<SmsSendLog> findByStatus(int status);

}