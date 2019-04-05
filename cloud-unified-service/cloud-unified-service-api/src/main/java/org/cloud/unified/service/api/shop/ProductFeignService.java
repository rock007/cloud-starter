package org.cloud.unified.service.api.shop;

import org.cloud.db.sys.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "productFeignService")
public interface ProductFeignService {

	@RequestMapping(value = "hello1")
    String hello(@RequestParam("name") String name);


    @RequestMapping(value = "hello2")
    SysUser hello(@RequestParam("name") String name, @RequestParam("age") Integer age);


    @RequestMapping(value = "hello3")
    String hello(@RequestBody SysUser user);
}
