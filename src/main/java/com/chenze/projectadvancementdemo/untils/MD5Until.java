package com.chenze.projectadvancementdemo.untils;

import com.chenze.projectadvancementdemo.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Until {
    public static String getMD5Str(String passWord) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((passWord+ Constant.SALT).getBytes()));
    }
}
