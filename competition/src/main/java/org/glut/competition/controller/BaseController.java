package org.glut.competition.controller;



import org.glut.competition.error.BusinessException;
import org.glut.competition.error.EmBusinessError;
import org.glut.competition.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/*
*
*返回web前端前处理异常，使前端能够很好的找到问题所在
* */
@RestControllerAdvice("org.glut.competition.controller")
public class BaseController {

    public static final String  CONTEND_TYPE_FROMED="application/x-www-form-urlencoded";

    //定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(Exception ex){

        if(ex instanceof BusinessException){
            BusinessException businessException=(BusinessException) ex;

            return CommonReturnType.create(businessException.getErrMsg(),Integer.toString(businessException.getErrCode()));
        }else{

            return CommonReturnType.create(EmBusinessError.UNKNOWN_ERROR.getErrMsg(),Integer.toString(EmBusinessError.UNKNOWN_ERROR.getErrCode()));
        }
    }
}
