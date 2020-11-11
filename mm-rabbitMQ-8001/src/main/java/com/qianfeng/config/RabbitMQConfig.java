package com.qianfeng.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author syb date 2020/10/30
 */
@Configuration
public class RabbitMQConfig {
  @Bean("trendQueue")
  public Queue queue(){
    return new Queue("trendQueue");
  }

}
