package org.cloud.unified.service.core.shop;

import org.cloud.db.sys.entity.SysUser;

import org.cloud.unified.service.api.shop.AddressService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressServiceImp implements AddressService {

	@Override
    public String getMyAddress(String name) {
        return "Hello" + name;
    }

}
