package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.entity.Article;
import com.qianfeng.mapper.ArticleMapper;
import com.qianfeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 分页查询全部数据
     * @param page 当前页
     * @param size 页面大小
     * @return 返回分页对象
     */
    @Override
    public IPage<Article> findAll(int page,int size) {
        IPage<Article> articleIPage = new Page<>(page,size);
        articleIPage = articleMapper.selectPage(articleIPage,null);
        return articleIPage;
    }
}
