package com.qianfeng.entity;

import lombok.Data;

@Data
public class Comment {
    String uid;
    String fid;
    Integer id;
    /*用户评论*/
    String info;
}
