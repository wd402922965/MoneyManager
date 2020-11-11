package com.qf.listen;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Created by dc on 2020/11/3.
 */
@Component
public class RabbitListen {
    @Autowired
    JavaMailSender javaMailSender;
    @RabbitListener(queues = "send-email")
    public void email(Map map){
        UUID.randomUUID().toString().substring(0,3);
    }
}
