package com.chenze.projectadvancementdemo.common;

import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;

public class ApiRestResponse<T> {
    Integer code;
    String msg;
    T data;

    public final static Integer OK_CODE=10000;
    public final static String OK_MSG="SUCCESS";
    public ApiRestResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiRestResponse(){
        this(OK_CODE,OK_MSG);
    }

    public static <T>ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }
    public static <T>ApiRestResponse<T> success(T result){
        ApiRestResponse<T> apiRestResponse = new ApiRestResponse<>();
        apiRestResponse.setData(result);
        return apiRestResponse;
    }

    public static <T>ApiRestResponse<T> error(Integer code,String msg){
        return new ApiRestResponse<>(code,msg);
    }

    public static <T>ApiRestResponse<T> error(MallExceptionEnum e){
        return new ApiRestResponse<>(e.getCode(),e.getMessage());
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
