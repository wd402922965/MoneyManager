package com.qianfeng.esTest;

import com.qianfeng.entity.Article;
import com.qianfeng.mapper.ArticleMapper;
import com.qianfeng.mapper.CollectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void TestInsert() throws IOException {

        List<Article> articles = articleMapper.selectList(null);

        for (Article article : articles) {
            Map map = new HashMap<>();
            map.put("ma_title", article.getMaTitle());
            map.put("ma_content", article.getMaContent());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String create = simpleDateFormat.format(article.getCreateTime());
            map.put("create_time", create);
            map.put("ma_isCom", article.getMaIsCom());
            map.put("u_id", article.getUId());
            map.put("ma_click", article.getMaClick());
            map.put("ma_com", article.getMaCom());
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String update = simpleDateFormat2.format(article.getUpdateTime());
            map.put("update_time", update);
            map.put("ma_istop", article.getMaIstop());
            IndexRequest request = new IndexRequest("article", "doc", article.getMaId());
            request.source(map);
            restHighLevelClient.index(request);
        }
    }

    @Test
    public void testSend() {
        Map map = new HashMap();
        map.put("key", "基金");
        rabbitTemplate.convertAndSend("trendQueue", map);
    }

    @Test
    public void testRedis() {
//        if (redisTemplate.hasKey("trend") == null) {
//            redisTemplate.opsForZSet().add("trend","基金",0);
//        }
//        redisTemplate.opsForZSet().incrementScore("trend","蚂蚁",1);
//        redisTemplate.opsForValue().set("test","test");
            Set trend = redisTemplate.opsForZSet().reverseRangeByScore("trend", 0, 10);
            System.out.println(trend);
            for (Object o : trend) {
                System.out.println(o);
            }
     }

}
