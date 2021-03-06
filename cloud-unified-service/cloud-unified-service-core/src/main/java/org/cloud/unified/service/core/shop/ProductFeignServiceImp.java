package org.cloud.unified.service.core.shop;

import org.cloud.db.sys.entity.SysUser;
import org.cloud.unified.service.api.shop.ProductFeignService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductFeignServiceImp implements ProductFeignService {

	@Override
    public String hello(String name) {
        return "Hello" + name;
    }

    @Override
    public SysUser hello(String name, Integer age) {
        return new SysUser();
    }

    @Override
    public String hello(SysUser user) {
        return "Hello" + user.getUsername() + ", " + user.getPhone();
    }
}
