package com.dxh.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dxh.blog.dao.pojo.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热的标签
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根据ids查询所有的tags
     * @param tagIds
     * @return
     */
    List<Tag> findTagByTagIds(List<Long> tagIds);
}
