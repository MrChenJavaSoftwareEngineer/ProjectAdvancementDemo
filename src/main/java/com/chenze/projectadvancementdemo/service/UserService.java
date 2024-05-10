package com.chenze.projectadvancementdemo.service;

import com.chenze.projectadvancementdemo.model.pojo.User;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    void register(String userName, String passWord) throws NoSuchAlgorithmException;

    User login(String userName, String passWord) throws NoSuchAlgorithmException;

    void updateInfo(String updateInfo);

    void updateInfo(String updateInfo, HttpSession session);

    boolean checkAdmin(User user);
}
