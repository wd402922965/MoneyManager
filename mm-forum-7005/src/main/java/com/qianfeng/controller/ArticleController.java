package com.qianfeng.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianfeng.entity.Article;
import com.qianfeng.entity.Collect;
import com.qianfeng.pojo.BaseResult;
import com.qianfeng.pojo.HighResult;
import com.qianfeng.pojo.ResultCode;
import com.qianfeng.service.ArticleService;
import com.qianfeng.utils.IdWorker;
import com.qianfeng.utils.SevenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ArticleController {

    @Autowired
    IdWorker idWorker;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询 方式为get
     * url: /forum/article/{page}/{size}
     * @param page 当前业
     * @param size 页面大小
     * @return
     */
    @GetMapping("/article/{page}/{size}")
    public BaseResult showAll(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        IPage<Article> articleIPage  = articleService.findAll(page, size);
        return new BaseResult(ResultCode.SUCCESS,articleIPage);
    }

    /**
     * 高亮查询
     * @param key 关键字
     * @param page 第几页
     * @param size 页面大小
     * @return
     */
    @GetMapping("/articleFind/{page}/{size}")
    public BaseResult findByKey(@RequestParam("key") String key,@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        if(!StringUtils.isEmpty(key)) {
            List<Article> articles = articleService.findByKey(key);
            Integer start = (page - 1) * size;
            Integer end = start + size;

            Integer total = articles.size();
            HighResult highResult = new HighResult();
            highResult.setTotal(total);
            highResult.setCurrent(page);
            highResult.setSize(size);
            highResult.setPages((total + size - 1)/size);

            if(highResult.getPages() < page){
                highResult.setData(articles);
            }else if(highResult.getPages() == page){
                List<Article> subArticle = articles.subList(start,total);
                highResult.setData(subArticle);
            }
            else{
                List<Article> subArticle = articles.subList(start,end);
                highResult.setData(subArticle);
            }
            return new BaseResult(ResultCode.SUCCESS,highResult);
        }
        return new BaseResult(ResultCode.FAIL,null);
    }

    /**
     * 从redis里获取热搜关键字，按照分数取前四个
     * @return
     */
    @GetMapping("/trend")
    public BaseResult getTrend(){
        List<String> trend = articleService.findTrend();
        return new BaseResult(ResultCode.SUCCESS,trend);
    }

    /**
     * 通过id查询某一个文章
     * @param id 文章id
     * @return
     */
    @GetMapping("/detail/{id}")
    public BaseResult findById(@PathVariable("id") String id){
        Article article = articleService.findById(id);
        return new BaseResult(ResultCode.SUCCESS,article);
    }

    /**
     * 推荐文章生成
     * @return
     */
    @GetMapping("/recommond")
    public BaseResult recommend(){
        List<Article> articles = articleService.findByTop();
        return new BaseResult(ResultCode.SUCCESS,articles);
    }

    /**
     * 获取文章正文内容，并生成静态化页面
     * @param data 生成静态化页面的数据，文章正文
     * @return
     */
    @RequestMapping("/createArticle")
    public String createArticle(String data,String maId){
        //创建一个正文
        Context context = new Context();
        //传入数据 第一个参数为html页面${}获取的名称
        //参数二为数据
        context.setVariable("data",data);

        //前端页面的路径
        //参数1：前端页面的名称
        //参数2 正文内容
        String shopDetail = templateEngine.process("shopDetail", context);
        //生成一个可以被浏览器解析的 静态页面字符串
//        return shopDetail;

        //上传到七牛云
        String upload = SevenUtils.upload(shopDetail,maId);
        System.out.println(upload);
        return upload;
    }

    /**
     * kmardown 上传图片
     * @param formData 上传的图片
     * @return 返回图片在七牛云的url
     * @throws IOException
     */
    @PostMapping("/uploadImage")
    public BaseResult getImageURL(@RequestParam("image") MultipartFile formData) throws IOException {
        byte[] bytes = formData.getBytes();
        String url = SevenUtils.uploadImage(bytes);
        return new BaseResult(ResultCode.SUCCESS,url);
    }

    /**
     * 用户编写文章
     * @param map
     * @return
     */
    @PostMapping("/addArticle")
    public BaseResult addArticle(@RequestBody Map map){
        //前端获得文章对象 Html格式
        String content = map.get("html").toString();
        //获得文章标题
        String title = map.get("title").toString();
        //当前用户由jwT解析获得uid
        String uid = "test";
        //文章生成id
        String maId = idWorker.nextId() + "";
        //将正文使用createArticle方法生成url
        String url = createArticle(content, maId);
        //将url存入到文章对象的内容里
        Article article = new Article();
        article.setMaContent(url);
        article.setMaId(maId);
        article.setUId(uid);
        article.setMaTitle(title);
        //返回文章上传成功的信息
        int i = articleService.addArticle(article);
        if(i != 0){
            return new BaseResult(ResultCode.SUCCESS,"文章发表成功");
        }
        return new BaseResult(ResultCode.FAIL,"文章发表失败");
    }

    @PostMapping("/collect")
    public BaseResult collect(@RequestBody Map map){
        //获得文章id
        String maId = map.get("maId").toString();
        //从cookie里获取用户id
        String uId = "12";
        Collect collect = new Collect();
        collect.setMaId(maId);
        collect.setUId(uId);
        int i = articleService.addCollect(collect);
        if(i == 1){
            return new BaseResult(ResultCode.SUCCESS,"文章收藏成功");
        }
        if(i == -1){
            return new BaseResult(ResultCode.FAIL,"文章已收藏");
        }
        return new BaseResult(ResultCode.FAIL,"收藏失败");
    }
}
