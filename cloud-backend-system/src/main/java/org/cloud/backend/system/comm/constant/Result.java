package org.cloud.backend.system.comm.constant;


import org.cloud.backend.system.comm.base.BaseResult;

/**
 * 系统常量枚举类
 */
public class Result extends BaseResult {

    public Result(ResultConstant upmsResultConstant, Object data) {
        super(upmsResultConstant.getCode(), upmsResultConstant.getMessage(), data);
    }

}
