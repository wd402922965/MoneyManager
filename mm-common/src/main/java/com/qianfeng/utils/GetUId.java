package com.qianfeng.utils;


import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class GetUId {
    public static String getUId(String token){
        DecodedJWT tokenInfo = JWTUtils.getTokenInfo(token);
        String uId = tokenInfo.getClaim("uId").asString();
        return uId;
    }

    public static String getToken(HttpServletRequest request){
        //获取携带token的cookies
        Cookie[] cookies = request.getCookies();
        //获取token
        String token = null;
        for(Cookie cook : cookies){
            if(cook.getName().equals("token")){
                token = cook.getValue();
            }
        }
        return token;
    }
}
