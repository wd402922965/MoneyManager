package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("mo_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String uId;
    private String uName;
    private String uPassword;
    private String uEmail;
    private Integer uCode;
    private String uPic;
    private Double uMoney;
    private Integer uRole;
    private String uUsername;
    private String uPhone;
    private Integer uAge;
    private Integer uSex;
    private String uProfessional;
}
