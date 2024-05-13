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
    TOKEN_WRONG(10012, "token解析失败"),
    CODE_ERROR(10013, "验证码错误~"),
    EMAIL_ADDRESS_ERROR(10014, "邮箱地址错误"),
    EMAIL_ADDRESS_EXIST(10015, "邮箱地址已被注册"),
    VERIFICATION_CODE_GENERATION_ERROR(10016, "验证码生成错误"),
    CATEGORY_EXISTED(10017, "目录已存在"),
    CATEGORY_NOT_EXISTED(10018, "目录不存在"),
    MAKE_FILE_FAIL(10019, "创建文件失败"),
    PRODUCT_NOT_EXIST(10020, "商品不存在"),
    DELETE_FAIL(10021, "删除失败"),
    PRODUCT_EXIST(10022, "商品已存在"),
    NOT_SALE(10023, "商品不进行售卖~"),
    LOWS_STOCK(10024, "商品库存不足"),
    CART_NOT_EXISTED(10025, "购物车不存在"),
    CART_EXISTED(10026, "购物车已存在"),
    NOT_ORDER(10027, "没有订单"),
    CANCEL_FAIL(10028, "取消订单失败"),
    FINISH_ERROR(10029, "订单完成失败"),
    NEED_NOT_PAY(10030, "订单不是没支付状态"),
    PAY_FAIL(10031, "支付错误"),
    FINISH_FAIL(10032, "订单完结失败"),
    NOT_DELIVER(10033, "订单不能进行发送"),
    DELIVER_FAIL(10034, "订单发送失败"),
    INSERT_FAILED(10035, "插入失败"),
    REQUEST_PARAM_ERROR(10036, "参数错误");
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
