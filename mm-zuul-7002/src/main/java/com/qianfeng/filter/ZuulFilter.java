package com.qianfeng.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.qianfeng.pojo.BaseResult;
import com.qianfeng.pojo.ResultCode;
import com.qianfeng.utils.GetUId;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulFilter extends com.netflix.zuul.ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        if("/forum/user/login".equals(requestURI)){
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("过滤器被执行");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //获取携带token的cookies
        Cookie[] cookies = request.getCookies();
        //获取token

        if(cookies == null){
            currentContext.getResponse().setContentType("application/json");
            currentContext.setResponseBody(JSON.toJSONString(new BaseResult(ResultCode.FAIL,"您还未登陆")));
            //这个请求最终不会被zuul转发到后端服务器
            currentContext.setSendZuulResponse(false);
            return null;
        }

        String token = null;
        for(Cookie cook : cookies){
            if(cook.getName().equals("token")){
                token = cook.getValue();
            }
        }

        if(token == null){
            System.out.println("token==null");
            currentContext.getResponse().setContentType("application/json");
            currentContext.setResponseBody(JSON.toJSONString(new BaseResult(ResultCode.FAIL,"您还未登陆")));
            //这个请求最终不会被zuul转发到后端服务器
            currentContext.setSendZuulResponse(false);
        }

        String uId = GetUId.getUId(token);
        if(uId==null){
            currentContext.getResponse().setContentType("application/json");
            currentContext.setResponseBody(JSON.toJSONString(new BaseResult(ResultCode.FAIL,"token已过期")));
            //这个请求最终不会被zuul转发到后端服务器
            currentContext.setSendZuulResponse(false);
        }
        //如果出现异常 也直接返回Result
        return null;
    }
}
