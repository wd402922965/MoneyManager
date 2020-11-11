package com.qianfeng;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.qianfeng.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.qianfeng.mapper")
public class ForumApplication_7005 {
    public static void main(String[] args) {
        SpringApplication.run(ForumApplication_7005.class,args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return  paginationInterceptor;
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
