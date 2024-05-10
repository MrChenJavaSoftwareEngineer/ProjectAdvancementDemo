package com.chenze.projectadvancementdemo.controller;

import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.model.pojo.User;
import com.chenze.projectadvancementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    //用户注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiRestResponse register(@RequestParam("UserName") String userName,
                                    @RequestParam("PassWord") String passWord) throws NoSuchAlgorithmException {
        userService.register(userName, passWord);
        return ApiRestResponse.success();
    }

    //用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiRestResponse login(@RequestParam("UserName") String userName,
                                 @RequestParam("PassWord") String passWord,
                                 HttpSession session) throws NoSuchAlgorithmException {
        User user = userService.login(userName, passWord);
        session.setAttribute(Constant.USER_KEY, user);
        return ApiRestResponse.success(user);
    }

    //用户个人签名
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ApiRestResponse updateInfo(@RequestParam("UpdateInfo") String updateInfo,
                                      HttpSession session) {
        userService.updateInfo(updateInfo,session);
        return ApiRestResponse.success();
    }

    //用户登出
    @RequestMapping(value = "/user/loginOut", method = RequestMethod.POST)
    public ApiRestResponse loginOut(HttpSession session) {
        session.removeAttribute(Constant.USER_KEY);
        return ApiRestResponse.success();
    }

    //管理员登录
    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    public ApiRestResponse adminLogin(@RequestParam("AdminName") String adminName,
                                      @RequestParam("PassWord") String passWord,
                                      HttpSession session) throws NoSuchAlgorithmException {
        User user = userService.login(adminName,passWord);
        if (!userService.checkAdmin(user)){
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN_ROLE);
        }
        session.setAttribute(Constant.ADMIN_KEY,user);
        return ApiRestResponse.success();
    }
}