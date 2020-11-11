package com.qianfeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qianfeng.entity.AComment;
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
    public List<AComment> findCommentByMaId(String maId) {
        QueryWrapper<AComment> wrapper = new QueryWrapper<>();
       wrapper.eq("ma_id", maId);
        List<AComment> AComments = commentMapper.selectList(wrapper);
        return AComments;
    }

    /**
     * 添加一条评论
     * @param AComment
     * @return
     */
    @Override
    public int addComment(AComment AComment) {
        int insert = commentMapper.insert(AComment);
        return insert;
    }
}
