package com.qianfeng;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MMEureka_7001 {
    public static void main(String[] args) {
        SpringApplication.run(MMEureka_7001.class,args);
    }
}
