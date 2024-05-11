package com.chenze.projectadvancementdemo.service.impl;

import com.chenze.projectadvancementdemo.exception.MallException;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.filter.UserFilter;
import com.chenze.projectadvancementdemo.model.dao.UserMapper;
import com.chenze.projectadvancementdemo.model.pojo.User;
import com.chenze.projectadvancementdemo.service.UserService;
import com.chenze.projectadvancementdemo.untils.MD5Until;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String userName, String passWord) throws NoSuchAlgorithmException {
        if (StringUtils.isEmpty(userName)){
            throw new MallException(MallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(passWord)){
            throw new MallException(MallExceptionEnum.NEED_USER_PASSWORD);
        }
        if (passWord.length()<8){
            throw new MallException(MallExceptionEnum.PASSWORD_SHORT);
        }
       User result = userMapper.selectByUserName(userName);
        if (result!=null){
            throw new MallException(MallExceptionEnum.USER_EXIST);
        }
        User user = new User();
        user.setUsername(userName);
        user.setPassword(MD5Until.getMD5Str(passWord));
         int count = userMapper.insertSelective(user);
         if (count==0){
             throw new MallException(MallExceptionEnum.USER_UPDATE_FAIL);
         }
    }

    @Override
    public User login(String userName, String passWord) throws NoSuchAlgorithmException {
        if (StringUtils.isEmpty(userName)){
            throw new MallException(MallExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(passWord)){
            throw new MallException(MallExceptionEnum.NEED_USER_PASSWORD);
        }
        if (passWord.length()<8){
            throw new MallException(MallExceptionEnum.PASSWORD_SHORT);
        }
        User result = userMapper.selectByUserName(userName);
        if (result==null){
            throw new MallException(MallExceptionEnum.NEED_REGISTER);
        }
        if (!result.getPassword().equals(MD5Until.getMD5Str(passWord))){
            throw new MallException(MallExceptionEnum.PASSWORD_FAIL);
        }
        result.setPassword(null);
        return result;
    }

    @Override
    public void updateInfo(String updateInfo) {
         User currUser = UserFilter.currentUser;
         currUser.setPersonalizedSignature(updateInfo);
        int count = userMapper.updateByPrimaryKeySelective(currUser);
        if (count==0){
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
    }

    @Override
    public boolean checkAdmin(User user) {
        return user.getRole().equals(2);
    }
}
