package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qianfeng.entity.User;
import com.qianfeng.mapper.UserMapper;
import com.qianfeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        //先查询用户名是否存在，在匹配密码
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("u_username",user.getUUsername());
        User userRee = userMapper.selectOne(wrapper);
        if(userRee != null){
            if(userRee.getUPassword().equals(user.getUPassword())){
                return userRee;
            }
        }
        return null;
    }
}
