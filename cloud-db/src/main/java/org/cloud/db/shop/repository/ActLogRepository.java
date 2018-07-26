package org.cloud.db.shop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.cloud.db.shop.entity.ActLog;

public interface ActLogRepository extends CrudRepository<ActLog, Long> {

    List<ActLog> findByActAndRefIdOrderByIdDesc(String act, Long refId);
    
    List<ActLog> findByActAndRefTableAndRefIdOrderByIdDesc(String act, String table_name,Long refId);
    
    @Query(value=" select  max(create_date)  from shop_act_log where  act=?  and ref_table=? and  ref_id=? " ,nativeQuery=true)
    Date findActLastDate(String act, String table_name,Long refId);
    
}