package com.qf.service.impl;

import com.qf.dao.UserDao;
import com.qf.service.UserService;

import com.qianfeng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by dc on 2020/11/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    //登录
    @Override
    public User findId(String userName) {
        return userDao.findId(userName);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public User findEmail(String email) {
        return userDao.findEmail(email);
    }

    @Override
    public Integer updateEmail(User user) {
        return userDao.updateEmail(user);
    }





   /* @Override
    public User findById(String id) {
        return userDao.findById(id);
    }*/
}
