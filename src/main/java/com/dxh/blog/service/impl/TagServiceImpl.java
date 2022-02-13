package com.dxh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.dxh.blog.dao.mapper.TagMapper;
import com.dxh.blog.dao.pojo.Tag;
import com.dxh.blog.service.TagService;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList=new ArrayList<>();
        for (Tag tag: tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisplus 无法进行多表查询
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    /**
     * 查询最热标签
     * @param limit
     * @return
     */
    @Override
    public Result hot(int limit) {
        List<Long> tagIds=tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds))
        {
            return Result.success(Collections.emptyList());
        }
        //需要 tagId 和tagName
        // select * from tag where id in (1,2,3)
        List<Tag> tagList=tagMapper.findTagByTagIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        List<Tag> tagList=tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag=tagMapper.selectById(id);
        //TagVo tagVo=copy(tag);
        return Result.success(tag);
    }


}
