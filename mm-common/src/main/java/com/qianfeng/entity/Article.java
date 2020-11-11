package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ID_WORKER_STR;

@TableName("m_article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @TableId(type = ID_WORKER_STR)
    private String maId; //ID
    private String maTitle; //文章标题
    private String maContent; //文章内容
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime; //发表时间
    @TableField("ma_isCom")
    private Integer maIsCom = 1; //是否可以评论 1 可以
    private String uId; //发表用户的id
    private Integer maClick = 0; //点击数
    private Integer maCom = 0; //评论数
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; //最新更新时间
    private Integer maIstop = 0; // 是否置顶
}
