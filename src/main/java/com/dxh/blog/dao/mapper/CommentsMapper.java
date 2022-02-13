package com.dxh.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxh.blog.dao.pojo.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsMapper extends BaseMapper<Comment> {
}
