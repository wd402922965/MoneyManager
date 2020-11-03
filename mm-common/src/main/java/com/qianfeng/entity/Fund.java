package com.qianfeng.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Fund {
    private  String id;
    private  Integer hot;
    private Double grim;
    private Double tworth;
    private String info;
    /*历史业绩id*/
    private Integer hid;
    private Integer level;
    private Double worth;
    /*关联公司id*/
    private Integer coid;
    private String name;
    private Integer tid;
    /*上架时间*/
    private Date date;
}
