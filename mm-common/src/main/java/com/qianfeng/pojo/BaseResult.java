package com.qianfeng.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResult {
    private boolean success;//是否成功
    private Integer code;//返回码
    private String message;//返回信息
    private Object data;//返回数据

    public BaseResult(ResultCode code){
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }
    public BaseResult(ResultCode code,Object data){
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    public BaseResult(Integer code,String message,boolean success){
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static  BaseResult SUCCESS(){
        return new BaseResult(ResultCode.SUCCESS);
    }
    public static BaseResult ERROR(){
        return  new BaseResult(ResultCode.SERVER_ERROR);
    }
    public static BaseResult FAIL(){
        return new BaseResult(ResultCode.FAIL);
    }
}
