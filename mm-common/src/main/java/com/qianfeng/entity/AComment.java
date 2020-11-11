package com.qianfeng.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ID_WORKER_STR;

@TableName("m_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AComment {
    @TableId(type = ID_WORKER_STR)
    private String mcId;
    private String uId;
    private String maId;
    private String mcComment;
    private String mccId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
