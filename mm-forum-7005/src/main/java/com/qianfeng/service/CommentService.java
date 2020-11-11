package com.qianfeng.service;

import com.qianfeng.entity.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> findCommentByMaId(String maId);

    public int addComment(Comment comment);
}
