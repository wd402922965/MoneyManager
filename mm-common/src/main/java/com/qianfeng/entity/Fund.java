package com.qianfeng.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Fund {
    String id;
    Integer hot;
    Double grim;
    Double tworth;
    String info;
    /*历史业绩id*/
    Integer hid;
    Integer level;
    Double worth;
    /*关联公司id*/
    Integer coid;
    String name;
    Integer tid;
    /*上架时间*/
    Date date;
}
