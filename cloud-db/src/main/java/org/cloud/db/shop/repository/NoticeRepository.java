package org.cloud.db.shop.repository;

import java.util.List;

import org.cloud.db.shop.entity.Notice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepository extends CrudRepository<Notice, Long>,JpaSpecificationExecutor<Notice> {

    List<Notice> findByToUser(String user);
    
    List<Notice> findByFromUser(String  user);
    
}