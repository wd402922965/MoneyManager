package com.qf.client;

import com.qianfeng.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by 54110 on 2020/10/22.
 */
@FeignClient(serviceId = "qflive-classes")
public interface UserClient {

    @RequestMapping("/findClassByUserId")
    public List<User> findClassByUserId(@RequestParam("id") String id);
}
