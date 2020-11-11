package com.qf.service;

import com.qianfeng.entity.User;

/**
 * Created by dc on 2020/11/4.
 */

public interface UserService {

    User findId(String userName);

    User findById(String id);

    Integer updateUser(User user);

    User findEmail(String email);

    Integer updateEmail(User user);


    //  User findById(String id);
}
