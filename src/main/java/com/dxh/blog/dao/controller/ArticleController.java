package com.dxh.blog.dao.controller;

import com.dxh.blog.common.aop.cache.Cache;
import com.dxh.blog.common.aop.log.LogAnnotation;
import com.dxh.blog.service.ArticleService;
import com.dxh.blog.vo.ArticleVo;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.params.ArticleParam;
import com.dxh.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;
    /**
     * 首页 查询文章列表
     * @param params
     * @return
     */
    @PostMapping
    @LogAnnotation(module="文章",operation="获取文章列表")
    public Result listArticle(@RequestBody PageParams params){
        //int i=1/0;
        return articleService.listArticle(params);
    }

    /**
     * 首页最热文章
     * @return
     */
    //此功能 加上缓存
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    @PostMapping("hot")
    public Result hotArticle(){
        int limit=6;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticle(){
        int limit=6;
        return articleService.newArticle(limit);
    }

    /**
     * 首页文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 根据id找文章
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        ArticleVo articleVo=articleService.findArticleById(articleId);
        return Result.success(articleVo);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
