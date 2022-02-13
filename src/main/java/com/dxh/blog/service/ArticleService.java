package com.dxh.blog.service;

import com.dxh.blog.vo.ArticleVo;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.params.ArticleParam;
import com.dxh.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param params
     * @return
     */
    Result listArticle(PageParams params);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 根据id查询文章
     * @param articleId
     * @return
     */
    ArticleVo findArticleById(Long articleId);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
