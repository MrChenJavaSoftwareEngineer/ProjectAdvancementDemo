package com.chenze.projectadvancementdemo.exception;

/**
 * 异常枚举
 */
public enum MallExceptionEnum {
    NEED_USER_NAME(10001,"用户名不能为空"),
    NEED_USER_PASSWORD(10002,"用户密码不能为空"),
    PASSWORD_FAIL(10003,"用户密码错误"),
    USER_EXIST(10004,"用户名已存在"),
    SYSTEM_ERROR(20000, "系统异常"),
    NEED_ADMIN_ROLE(10005, "需要管理员权限"),
    PASSWORD_SHORT(10006, "密码太短，不安全~"),
    UPDATE_FAIL(10007, "更新失败"),
    USER_UPDATE_FAIL(10008, "用户注册失败"),
    NEED_REGISTER(10009, "需要注册"),
    NEED_LOGIN(10010, "需要登录"),
    TOKEN_EXPIRE(10011, "token过期"),
    TOKEN_WRONG(10012, "token解析失败");
    //异常码
    Integer code;
    //异常信息
    String message;

    MallExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
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
}
