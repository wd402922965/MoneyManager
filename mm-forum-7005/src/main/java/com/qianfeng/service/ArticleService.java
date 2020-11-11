package com.qianfeng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.entity.Article;
import com.qianfeng.entity.Collect;

import java.util.List;
import java.util.Set;

/*
*
* 文章服务接口
* */
public interface ArticleService {
    public IPage<Article> findAll(int page, int size);

    public List<Article> findByKey(String key);

    public List<String> findTrend();

    public Article findById(String id);

    public List<Article> findByTop();

    public int addArticle(Article article);

    public  int addCollect(Collect collect);
}
