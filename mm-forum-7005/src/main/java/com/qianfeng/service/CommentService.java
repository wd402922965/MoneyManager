package com.qianfeng.service;

import com.qianfeng.entity.AComment;

import java.util.List;

public interface CommentService {
    public List<AComment> findCommentByMaId(String maId);

    public int addComment(AComment AComment);
}
