package com.qianfeng.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标题高亮搜索返回实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighResult {
    private Object data;
    private Integer total;
    private Integer size;
    private Integer current;
    private Integer pages;
}

