package com.chenze.projectadvancementdemo.exception;

/**
 * 统一异常
 */
public class MallException extends RuntimeException{
    final Integer code;
    final String msg;

    public MallException(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public MallException(MallExceptionEnum mallExceptionEnum){
        this(mallExceptionEnum.getCode(),mallExceptionEnum.getMessage());
    }
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
