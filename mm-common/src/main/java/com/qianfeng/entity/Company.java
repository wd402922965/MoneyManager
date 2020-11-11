package com.qianfeng.entity;

import lombok.Data;

import java.util.Date;
/*发行公司表*/
@Data
public class Company {
    private String id;
    private  String info;
    private  String name;
    private  Date time;
}
