package com.qf.dao;

import com.qianfeng.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by dc on 2020/11/5.
 */
@Mapper
public interface UserDao {

    User findId(String userName);

    User findById(String id);

    Integer updateUser(User user);

    User findEmail(String email);

    Integer updateEmail(User user);

    //  User findById(String id);
}
