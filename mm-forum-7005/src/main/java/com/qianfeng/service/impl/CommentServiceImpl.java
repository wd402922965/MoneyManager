package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qianfeng.entity.Comment;
import com.qianfeng.mapper.CommentMapper;
import com.qianfeng.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据文章ID查询全部评论
     * @param maId
     * @return
     */
    @Override
    public List<Comment> findCommentByMaId(String maId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
       wrapper.eq("ma_id", maId);
        List<Comment> comments = commentMapper.selectList(wrapper);
        return comments;
    }

    /**
     * 添加一条评论
     * @param comment
     * @return
     */
    @Override
    public int addComment(Comment comment) {
        int insert = commentMapper.insert(comment);
        return insert;
    }
}
