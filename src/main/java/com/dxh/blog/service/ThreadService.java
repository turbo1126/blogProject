package com.dxh.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dxh.blog.dao.mapper.ArticleMapper;
import com.dxh.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article){
        Article updateArticle=new Article();
        updateArticle.setViewCounts(article.getViewCounts()+1);
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();

        wrapper.eq(Article::getId,article.getId());
        wrapper.eq(Article::getViewCounts,article.getViewCounts());

        //int update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper);
        articleMapper.update(updateArticle,wrapper);

        //休眠5秒 证明不会影响主线程的使用
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
