package com.dxh.blog.service;

import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.params.CommentParam;


public interface CommentsService {
    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
