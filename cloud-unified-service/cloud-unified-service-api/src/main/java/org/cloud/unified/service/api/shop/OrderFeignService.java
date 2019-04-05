package org.cloud.unified.service.api.shop;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "orderFeignService")
public interface OrderFeignService {

	@RequestMapping(value = "/submit-order")
    String submitOrder(@RequestParam("name") String name);

}
