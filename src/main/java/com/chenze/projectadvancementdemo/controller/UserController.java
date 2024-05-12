package com.chenze.projectadvancementdemo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.model.pojo.User;
import com.chenze.projectadvancementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    String token=null;

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
                                 @RequestParam("PassWord") String passWord) throws NoSuchAlgorithmException {
        User user = userService.login(userName, passWord);
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        token= JWT.create()
                .withClaim(Constant.USER_ID,user.getId())
                .withClaim(Constant.USER_NAME,user.getUsername())
                .withClaim(Constant.USER_ROLE,user.getRole())
                .withExpiresAt(new Date(System.currentTimeMillis()+Constant.EXPIRE_TIME))
                .sign(algorithm);
        return ApiRestResponse.success(token);
    }

    //用户个人签名
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ApiRestResponse updateInfo(@RequestParam("UpdateInfo") String updateInfo) {
        userService.updateInfo(updateInfo);
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
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        token= JWT.create()
                .withClaim(Constant.USER_ID,user.getId())
                .withClaim(Constant.USER_NAME,user.getUsername())
                .withClaim(Constant.USER_ROLE,user.getRole())
                .withExpiresAt(new Date(System.currentTimeMillis()+Constant.EXPIRE_TIME))
                .sign(algorithm);
        return ApiRestResponse.success(token);
    }
}