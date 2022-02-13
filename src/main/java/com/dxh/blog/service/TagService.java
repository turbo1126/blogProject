package com.dxh.blog.service;

import com.dxh.blog.dao.pojo.Tag;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hot(int limit);

    Result findAll();

    Result findDetailById(Long id);
}
