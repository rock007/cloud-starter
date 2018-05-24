package org.cloud.unified.service.core.shop;

import org.cloud.db.sys.entity.SysUser;

import org.cloud.unified.service.api.shop.AddressFeignService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressFeignServiceImp implements AddressFeignService {

	@Override
    public String getMyAddress(String name) {
        return "Hello" + name;
    }

}
