package org.cloud.unified.service.api.sys;

import org.cloud.db.sys.entity.Permission;
import org.cloud.db.sys.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "sysFeignService")
public interface SysFeignService {

    @RequestMapping(value = "/findUserByName")
    public SysUser findUserByName(String username);

    @RequestMapping(value = "/findUserById")
    public SysUser findUserById(Long user_id);

    @RequestMapping(value = "/findPermissionsByUserId")
    public List<Permission> findPermissionsByUserId(Long systemId,Long userId);
}
