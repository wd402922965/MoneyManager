package com.qianfeng.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public BaseResult(){
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
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
