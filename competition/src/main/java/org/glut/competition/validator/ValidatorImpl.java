package org.glut.competition.validator;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;



@Component   //定义为一个spring  bean;,方便扫描
public class ValidatorImpl implements InitializingBean {

    private Validator validator;
    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate的validator通过工厂的初始化方式使其实例化
        this.validator= Validation.buildDefaultValidatorFactory().getValidator();
    }

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
         ValidationResult result=new ValidationResult();
         Set<ConstraintViolation<Object>> constraintViolationSet= validator.validate(bean);
         if (constraintViolationSet.size()>0){
             //有错误
             result.setHasErrors(true);

/*             constraintViolationSet.forEach(constraintViolation->{
                 String errMsg=constraintViolation.getMessage();
                 String propertyName=constraintViolation.getPropertyPath().toString();
                 result.getErrorMsgMap().put(propertyName,errMsg);
             });*/
             //用常规方式改下
             for (ConstraintViolation<Object> set:constraintViolationSet) {
                 String errMsg = set.getMessage();
                 String propertyName = set.getPropertyPath().toString();
                 result.getErrorMsgMap().put(propertyName,errMsg);
             }
         }
         return result;

    }


}
