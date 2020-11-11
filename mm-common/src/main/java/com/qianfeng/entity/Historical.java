package com.qianfeng.entity;

import lombok.Data;

/*历史业绩*/
@Data
public class Historical {
    private  String date;
    private  Double limit;
    private Integer rank;
    private Integer id;
}
