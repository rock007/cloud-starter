package org.cloud.shop.service.imp;

import org.cloud.db.sys.entity.SysUser;
import org.cloud.shop.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImp implements ProductService{

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
