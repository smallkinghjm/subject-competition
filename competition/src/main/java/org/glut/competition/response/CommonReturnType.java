package org.glut.competition.response;

/*
* 若返回成功，status等于"success"，data存放返回的数据，可调用create()和create(Object result)
* 若返回失败，status等于"自定义错误码",data返回错误信息，调用create(Object result,String status)
* */

public class CommonReturnType {

    //表明对应请求的返回结果“success"或”自定义错误码"
    private String status;

    //若status=success,则data内返回前端需要的的json数据；
    //若status=自定义错误码,则data内使用通用的错误码格式；
    private Object data;


    //定义一个通用的创建方法
    public static CommonReturnType create(){
        CommonReturnType commonReturnType=new CommonReturnType();
        commonReturnType.setStatus("success");
        return commonReturnType;
    }
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
        //return CommonReturnType.create(result,"666666");
    }

    public static CommonReturnType create(Object result,String status){
        CommonReturnType commonReturnType=new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setData(result);
        return commonReturnType;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}