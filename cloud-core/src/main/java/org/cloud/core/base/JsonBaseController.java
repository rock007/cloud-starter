package org.cloud.core.base;

import org.cloud.core.model.JsonBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public abstract class  JsonBaseController {

    protected static final Logger logger = LoggerFactory.getLogger(JsonBaseController.class);

    @ExceptionHandler
    public @ResponseBody
    JsonBody<String> handleException(HttpServletRequest request, Exception ex){

        logger.error("handleException", ex);

        return new JsonBody<String>(-1,"操作失败，系统出现异常",ex.getMessage());
    }
}
