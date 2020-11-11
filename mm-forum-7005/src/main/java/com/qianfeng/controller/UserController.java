package com.qianfeng.controller;

import com.qianfeng.entity.User;
import com.qianfeng.pojo.BaseResult;
import com.qianfeng.pojo.ResultCode;
import com.qianfeng.service.impl.UserService;
import com.qianfeng.service.impl.UserServiceImpl;
import com.qianfeng.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController  {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResult login(@RequestBody Map map){
        //从前端获取账户名和密码
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        User user = new User();
        user.setUUsername(username);
        user.setUPassword(password);
        //执行登陆方法
        User userRes = userService.login(user);
        //如果登陆成功返回1
        if(userRes != null){
            //登陆成功返回一个token
            //参数1 为用户的UID
            //参数2 为用户的UnameName
            Map mapJWT  = new HashMap();
            mapJWT.put("uId",userRes.getUId());
            String token = JWTUtils.getJWT(userRes.getUUsername(),mapJWT);
            return new BaseResult(ResultCode.SUCCESS,token);
        }
        return new BaseResult(ResultCode.FAIL,"登陆失败，账号或密码错误");
    }
}
