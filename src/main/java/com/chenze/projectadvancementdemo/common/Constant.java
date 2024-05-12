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

    public interface ProductListOrderBy{
       Set<String> PRICE_ASC_DESC=Sets.newHashSet("price asc","price desc");
    }
}
