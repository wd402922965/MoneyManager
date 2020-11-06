package com.qf.controller;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.qf.service.UserService;
import com.qianfeng.entity.User;
import com.qianfeng.pojo.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dc on 2020/11/4.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //登录
    @ResponseBody
    @RequestMapping("/login")
    public BaseResult login(@RequestBody User user){
        BaseResult baseResult = new BaseResult();
        //获取前端输入的userName
        String userName = user.getUserName();
        //用前端输入的userName查询user对象
        User user1 = userService.findId(userName);

            if(user1!=null){
                //获取前端输入的密码
                String password = user.getPassword();
                //根据前端输入的用户名查出来的对象  获取到的密码
                String password1 = user1.getPassword();
                // User user2 = userService.findId2(password);
                if (password.equals(password1)){
                        //如果user2对象不为空,说明用户名密码正确,将用户名密码放入jwt生成token
                    long l = System.currentTimeMillis();
                    l += 1*60*1000;
                    Date date = new Date(l);
                    //定义加密的算法  私钥
                    Algorithm algorithmHS = Algorithm.HMAC256("secret");
                    Map map = new HashMap<>();
                    map.put("alg", "HS256");
                    map.put("typ", "jwt");
                    //头部部分
                    String token = JWT.create().withHeader(map).
                            //创建的主题
                                    withSubject("this is token").
                            //创建者
                                    withIssuer("dc").
                            //签发的时间
                                    withIssuedAt(new Date()).
                            //自定义的信息
                                    withClaim("username", user1.getUserName()).
                                    withClaim("password",user1.getPassword()).
                            //过期时间
                                    withExpiresAt(date).
                            //签名
                                    sign(algorithmHS);
                    baseResult.setMessage(token);
                    //     System.out.println(token);
                    baseResult.setCode(1000);
                    //      baseResult.setData(user1.getUserName()+user1.getPassword()+"登录成功");
                    }else {
                    baseResult.setMessage("密码错误");
                    baseResult.setCode(1003);
                }
                }else {
                baseResult.setMessage("用户不存在");
                baseResult.setCode(1002);
            }
                return baseResult;
    }

//按id查看用户的所有信息
    @RequestMapping("/findByUserId")
    public User findById(@RequestBody User user){

        String id = user.getId();
        User user1 = userService.findById(id);

        return user1;
    }
//按id修改用户信息
    @RequestMapping("/updateUser")
    public Integer updateUser(@RequestBody User user){
        Integer user1 = userService.updateUser(user);

        return user1;
    }

    //修改绑定邮箱
    @RequestMapping("/updateEmail")
    public Integer updateEmail(@RequestBody User user){
        String email = user.getEmail();
        User user2 = userService.findEmail(email);
        if(user2==null){
            Integer user1 = userService.updateEmail(user);
            return user1;
        }
        return null;
    }
}
