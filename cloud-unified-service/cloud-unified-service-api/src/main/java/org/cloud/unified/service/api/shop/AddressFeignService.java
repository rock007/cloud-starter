package org.cloud.unified.service.api.shop;

import org.cloud.db.sys.entity.SysUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "addressFeignService")
public interface AddressFeignService {

	@RequestMapping(value = "get-my-address")
    String getMyAddress(@RequestParam("name") String name);

}
