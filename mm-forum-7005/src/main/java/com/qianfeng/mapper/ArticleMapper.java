package com.qianfeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianfeng.entity.Article;
import org.springframework.stereotype.Repository;

/*
* 文章类的mapper
* */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
}
