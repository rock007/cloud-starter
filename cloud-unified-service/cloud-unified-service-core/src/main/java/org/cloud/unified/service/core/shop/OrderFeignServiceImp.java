package org.cloud.unified.service.core.shop;

import org.cloud.db.sys.entity.SysUser;
import org.cloud.unified.service.api.shop.OrderFeignService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderFeignServiceImp implements OrderFeignService {

	@Override
    public String submitOrder(String name) {
        return "Hello" + name;
    }
}
