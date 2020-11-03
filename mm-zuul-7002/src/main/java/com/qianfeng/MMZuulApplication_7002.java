package com.qianfeng;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import javax.swing.*;


@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class MMZuulApplication_7002 {
    public static void main(String[] args) {
        SpringApplication.run(MMZuulApplication_7002.class,args);
    }
}
