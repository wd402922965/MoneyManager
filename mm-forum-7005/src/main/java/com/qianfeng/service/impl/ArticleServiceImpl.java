package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianfeng.entity.Article;
import com.qianfeng.entity.Collect;
import com.qianfeng.mapper.ArticleMapper;
import com.qianfeng.mapper.CollectMapper;
import com.qianfeng.service.ArticleService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Wrapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 分页查询全部数据
     *
     * @param page 当前页
     * @param size 页面大小
     * @return 返回分页对象
     */
    @Override
    public IPage<Article> findAll(int page, int size) {
        //条件构造器
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("ma_istop").orderByDesc("update_time");

        IPage<Article> articleIPage = new Page<>(page, size);
        articleIPage = articleMapper.selectPage(articleIPage, wrapper);
        return articleIPage;
    }

    /**
     * 关键字搜素
     * @param key  标题关键字
     * @return
     */
    @Override
    public List<Article> findByKey(String key) {
        //将查询到的数据放入redis当作缓存，第二次查询的时候在controller手动做分页


        //进行标题高亮查询
        SearchRequest request = new SearchRequest("article");
        request.types("doc");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MultiMatchQueryBuilder s = QueryBuilders
                .multiMatchQuery(key, "ma_title");

        //设置高亮展示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //设置前缀
        highlightBuilder.preTags("<font style='color:red'>");
        //设置后缀
        highlightBuilder.postTags("</font>");

        //设置高亮展示的mapping
        highlightBuilder.fields().add(new HighlightBuilder.Field("ma_title"));

        //设置搜索源的搜索方式
        searchSourceBuilder.query(s);
        //将高粱设置放到搜索源中
        searchSourceBuilder.highlighter(highlightBuilder);

        //将搜索源放入request
        request.source(searchSourceBuilder);

        //执行搜索
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Article> articles = new ArrayList<>();

        //如果查到关键词 使用rabbitMQ向redis里添加热搜词
        long totalHits = search.getHits().getTotalHits();
        System.out.println(totalHits);
        if(totalHits!= 0){
            Map map = new HashMap();
            map.put("key",key);
            rabbitTemplate.convertAndSend("trendQueue",map);
        }
        //解析结果
        SearchHit[] hits = search.getHits().getHits();

        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String id = hit.getId();
            String content = (String)sourceAsMap.get("ma_content");

            //日期格式转换
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date create = null;
            Object create_time = sourceAsMap.get("create_time");
            try {
               create = simpleDateFormat.parse(create_time.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Integer isCom = (Integer)sourceAsMap.get("ma_isCom");
            String uId = (String)sourceAsMap.get("u_id");
            Integer click = (Integer)sourceAsMap.get("ma_click");
            Integer com = (Integer)sourceAsMap.get("ma_com");

            //日期格式转换
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date update = null;
            try {
                update = simpleDateFormat2.parse(sourceAsMap.get("update_time").toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Integer isTop = (Integer)sourceAsMap.get("ma_istop");

            /*
            * 高亮获取
            * */
            String title = null;
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(highlightFields != null){
                HighlightField titleHigh = highlightFields.get("ma_title");
                if(titleHigh!=null){
                    Text[] fragments = titleHigh.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text fragment : fragments) {
                        stringBuffer.append(fragment);
                    }
                    title = stringBuffer.toString();
                }
            }
            Article article = new Article(id,title,content,create,isCom,uId,click,com,update,isTop);
            articles.add(article);
        }
        return articles;
    }

    /**
     * 获取热搜字段
     * @return 返回前4个热搜字段
     */
    @Override
    public List<String> findTrend() {
        Set trend = redisTemplate.opsForZSet().reverseRange("trend", 0, -1);
        ArrayList trends = new ArrayList<>(trend);
        List<String> list = null;
        if(trends.size() >= 4) {
            list = trends.subList(0, 4);
            return list;
        }
        return trends;
    }

    /**
     * 文章详细查询
     * @param id 文章id为参数查询
     * @return
     */
    @Override
    public Article findById(String id) {
        Article article = articleMapper.selectById(id);
        return article;
    }

    /**
     * 查询所有置顶的文章，并按点击数由高到低查询，选取前4条
     * @return
     */
    @Override
    public List<Article> findByTop() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("ma_istop",1).orderByDesc("ma_click");
        List<Article> articles = articleMapper.selectList(wrapper);
        if(articles.size()<=4){
            return articles;
        }else {
            List<Article> articles2 = articles.subList(0, 4);
            return articles2;
        }
    }

    //添加文章
    @Override
    public int addArticle(Article article) {
        int insert = articleMapper.insert(article);
        return insert;
    }

    //添加收藏
    @Override
    public int addCollect(Collect collect) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",collect.getUId()).eq("ma_id",collect.getMaId());
        Collect collect1 = collectMapper.selectOne(wrapper);
        if(collect1 != null){
            return -1;
        }
        int insert = collectMapper.insert(collect);
        return insert;
    }
}
