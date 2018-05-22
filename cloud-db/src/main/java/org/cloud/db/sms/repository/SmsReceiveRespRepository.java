/**
*@Author: sam
*@Date: 2017年11月14日
*@Copyright: 2017  All rights reserved.
*/
package org.cloud.db.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import org.cloud.db.sms.entity.SmsReceiveResp;

public interface SmsReceiveRespRepository extends CrudRepository<SmsReceiveResp, Long>,JpaSpecificationExecutor<SmsReceiveResp> {

	List<SmsReceiveResp> findByFlag(Integer flag);
}

