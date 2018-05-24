package org.cloud.unified.service.api.shop;

import org.cloud.db.sys.entity.SysUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "orderFeignService")
public interface OrderFeignService {

	@RequestMapping(value = "/submit-order")
    String submitOrder(@RequestParam("name") String name);

}
