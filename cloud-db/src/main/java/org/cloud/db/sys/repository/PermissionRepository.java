package org.cloud.db.sys.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.cloud.db.sys.entity.Permission;

import java.util.List;

/**
 * Created by sam on 2017/7/7.
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> ,JpaSpecificationExecutor<Permission> {

    @Query(value=" select\n" +
            "\t\t\t*\n" +
            "\t\tfrom sys_permission up where up.`status`=1 and up.`system_id`=:systemId and up.permission_id in (\n" +
            "\t\t\tselect permission_id from sys_role_permission urp where urp.role_id in (\n" +
            "\t\t\t\tselect uur.role_id role_id from sys_user_role uur where uur.user_id=1\n" +
            "\t\t\t)\n" +
            "\t\t\tunion\n" +
            "\t\t\tselect permission_id from sys_user_permission uup1 where uup1.user_id=:user_id and uup1.type=1\n" +
            "\t\t)\n" +
            "\t\tand up.permission_id not in (\n" +
            "\t\t\tselect permission_id from sys_user_permission uup2 where uup2.user_id=:user_id and uup2.type=-1\n" +
            "\t\t) order by up.orders asc  ",nativeQuery =true)
    List<Permission> findByUserId(@Param("systemId")Long systemId, @Param("user_id")Long user_id);

    @Query(value="select DISTINCT s.* "+
			" from sys_permission s  "+
			"	INNER JOIN sys_role_permission  u on s.permission_id=u.permission_id "+
			"  INNER JOIN  sys_user_role  r on r.role_id = u.role_id "+
			" where r.user_id=:user_id and pid=:pid and s.system_id = :systemId and s.type = 1 and s.`status`=1 "+
			" ORDER BY s.orders desc",nativeQuery =true)
    List<Permission> find4Menu(@Param("systemId")Long systemId,@Param("user_id")Long user_id,@Param("pid")Long pid);
    
    List<Permission> findBySystemIdAndPidAndType(Long systemId,Long pid,Integer mtype);

    List<Permission> findByPidAndTypeAndStatus(Long pid,Integer mtype,Integer Status);

}
