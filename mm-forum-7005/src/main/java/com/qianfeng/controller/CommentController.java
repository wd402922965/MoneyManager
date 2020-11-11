package com.qianfeng.controller;

import com.qianfeng.entity.Comment;
import com.qianfeng.pojo.BaseResult;
import com.qianfeng.pojo.ResultCode;
import com.qianfeng.service.CommentService;
import com.qianfeng.utils.GetUId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 根据文章ID查看文章的评论
     * @param maId 文章ID
     * @return
     */
    @GetMapping("/comment/{maId}")
    public BaseResult findByMaId(@PathVariable("maId") String maId){
        List<Comment> comments = commentService.findCommentByMaId(maId);
        return new BaseResult(ResultCode.SUCCESS,comments);
    }

    /**
     * 添加一条评论
     * @param map 获得评论内容和当前文章ID及用户Id
     * @return
     */
    @PostMapping("/addComment")
    public BaseResult addComment(@RequestBody Map map, HttpServletRequest request){
        String com = map.get("comment").toString();
        String maId = map.get("maId").toString();

        String token = GetUId.getToken(request);
        String uId = GetUId.getUId(token);
        System.out.println(uId);

        //通过jwt获得当前uid,先用假数据代替
        Comment comment = new Comment();
        comment.setMaId(maId);
        comment.setMcComment(com);
        comment.setUId(uId);
        int i = commentService.addComment(comment);
        if( i != 0){
            return new BaseResult(ResultCode.SUCCESS,"评论成功");
        }
        else{
            return new BaseResult(ResultCode.FAIL,"评论失败");
        }
    }
}
