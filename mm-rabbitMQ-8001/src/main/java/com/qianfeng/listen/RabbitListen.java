package com.qianfeng.listen;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitListen {
    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 添加热搜字段到redis 使用zset方法
     * @param map 获取搜索字段
     */
    @RabbitListener(queues = "trendQueue")
    public void workQueue(Map map){
        String key = (String)map.get("key");
        System.out.println(key);

        redisTemplate.opsForZSet().incrementScore("trend",key,1);
    }
}
