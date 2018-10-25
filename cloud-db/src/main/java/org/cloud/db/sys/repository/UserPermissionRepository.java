package org.cloud.db.sys.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.cloud.db.sys.entity.UserPermission;

import java.util.List;

/**
 * Created by sam on 2017/7/7.
 */
public interface UserPermissionRepository extends CrudRepository<UserPermission, Long> {

    List<UserPermission> findByUserId(Long userId);
    
    @Transactional
    Long deleteByPermissionId(Long permissionId);

    //和上面不同，执行后要flush
    @Transactional
    List<UserPermission> removeByPermissionId(Long permissionId);

}
