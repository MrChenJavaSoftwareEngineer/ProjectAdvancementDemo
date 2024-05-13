package com.chenze.projectadvancementdemo.common;


import com.google.common.collect.Sets;

import java.util.Set;

public class Constant {
    public static final String SALT="8svbsvjkweDF,.03[";
    public static final String JWT_KEY = "jwt_key";
    public static final long EXPIRE_TIME = 60*60*60*1000;
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_ROLE = "user_role";
    public static final String JWT_TOKEN = "jwt_token";
    public static final String EMAIL_FROM = "3648901811@qq.com";
    public static final String EMAIL_SUBJECT="您的验证码";
    public static final String FILE_UPLOAD_DIR = "D:/fileImage/";
    public static final int IMAGE_LENGTH = 400;
    public static final int IMAGE_HEIGHT = 400;
    public static final String WATER_MARK_JPG = "waterMark.jpg";
    public static final float IMAGE_OPACITY = 0.5f;

    public static String orderStatusName(Integer statusCode){
        switch (statusCode){
            case 0:
                return OrderStatus.CANCEL.getStatusCodeName();
            case 10:
                return OrderStatus.NOT_PAY.getStatusCodeName();
            case 20:
                return OrderStatus.PAY.getStatusCodeName();
            case 30:
                return OrderStatus.DELIVER.getStatusCodeName();
            case 40:
                return OrderStatus.FINISH.getStatusCodeName();
            default:
                return null;
        }
    }



    public enum OrderStatus{
        CANCEL(0,"订单取消"),
        NOT_PAY(10,"订单未支付"),
        PAY(20,"订单已支付"),
        DELIVER(30,"订单货物进行发送中"),
        FINISH(40,"订单已完成");
        private Integer statusCode;
        private String statusCodeName;

        OrderStatus(Integer statusCode, String statusCodeName) {
            this.statusCode = statusCode;
            this.statusCodeName = statusCodeName;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusCodeName() {
            return statusCodeName;
        }

        public void setStatusCodeName(String statusCodeName) {
            this.statusCodeName = statusCodeName;
        }
    }

    public enum CartSelected{
        CART_SELECTED(1,"已勾选"),
        CART_NOT_SELECTED(0,"未勾选");
        private Integer selectedCode;
        private String selectedMsg;

        CartSelected(Integer selectedCode, String selectedMsg) {
            this.selectedCode = selectedCode;
            this.selectedMsg = selectedMsg;
        }

        public Integer getSelectedCode() {
            return selectedCode;
        }

        public void setSelectedCode(Integer selectedCode) {
            this.selectedCode = selectedCode;
        }

        public String getSelectedMsg() {
            return selectedMsg;
        }

        public void setSelectedMsg(String selectedMsg) {
            this.selectedMsg = selectedMsg;
        }
    }
    public interface ProductStatus{
        Integer NOT_SALE=0;
    }

    public interface ProductListOrderBy{
       Set<String> PRICE_ASC_DESC=Sets.newHashSet("price asc","price desc");
    }
}
