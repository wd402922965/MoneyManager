package com.qianfeng;


import com.qianfeng.utils.GetUId;
import com.qianfeng.utils.IdWorker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestClass {
    public static void main(String[] args) throws UnknownHostException {
//        IdWorker idWorker = new IdWorker();
//        String s = String.valueOf(idWorker.nextId());
//        System.out.println(s);

//        String[] s = {"aa","bb","cc"};
//        List<String> list = new ArrayList<String>(s.length);
//        Collections.addAll(list, s);
//        List<String> strings = list.subList(0, 2);
//        for (String string : strings) {
//            System.out.println(string);
//        }

        String uId = GetUId.getUId("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1SWQiOiIxMzI1NjM3MDEyOTAzOTg1MTUyIiwic3ViIjoiYWRtaW4iLCJleHAiOjE2MDQ4OTQ1NDksImlhdCI6MTYwNDg5NDQ4OX0.1LvKGfKu_RFgeL0fVPoSHSnNpc7TmagLQl2MSVVRRwI");
        System.out.println(uId);
    }
}
