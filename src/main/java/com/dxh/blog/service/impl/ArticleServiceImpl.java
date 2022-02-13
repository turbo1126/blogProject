package com.dxh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dxh.blog.dao.dos.Archives;
import com.dxh.blog.dao.mapper.ArticleBodyMapper;
import com.dxh.blog.dao.mapper.ArticleMapper;
import com.dxh.blog.dao.mapper.ArticleTagMapper;
import com.dxh.blog.dao.pojo.Article;
import com.dxh.blog.dao.pojo.ArticleBody;
import com.dxh.blog.dao.pojo.ArticleTag;
import com.dxh.blog.dao.pojo.SysUser;
import com.dxh.blog.service.*;
import com.dxh.blog.utils.UserThreadLocal;
import com.dxh.blog.vo.*;
import com.dxh.blog.vo.params.ArticleParam;
import com.dxh.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl  implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;  //用来处理分页
    @Autowired
    private TagService tagService;  //用来查询文章的所有标签
    @Autowired
    private SysUserService sysUserService; //用来查询文章的作者
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage=articleMapper.listArticle(page,pageParams.getCategoryId()
        ,pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());

        return Result.success(copyList(articleIPage.getRecords(),true,true,true));

    }
//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 1.分页查询article数据库表
//         */
//        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        if (pageParams.getCategoryId()!=null){
//            //若查看 专属类别下的文章 则加上此 条件
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        if (pageParams.getTagId()!=null){
//            //若查看 专属类别下的文章 则加上此 条件
//            LambdaQueryWrapper<ArticleTag> wrapper=new LambdaQueryWrapper<>();
//            wrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags=articleTagMapper.selectList(wrapper);
//            List<Long> articleIdList=new ArrayList<>();
//            for (ArticleTag articleTag: articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size()>0){
//                queryWrapper.in(Article::getId,articleIdList);
//            }
//        }
//        //按照日期 和 是否置顶 排序
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        //list不能直接返回
//        List<ArticleVo> articleVoList =copyList(records,true,true);
//
//        return Result.success(articleVoList);
//    }
    //把List<Article> --->  List<ArticleVo> 返回给首页
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record: records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }//重载
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record: records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,false));
        }
        return articleVoList;
    }//重载
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record: records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }
    //把article转换成 articleVo
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo=new ArticleVo();
        System.out.println(article+"ppppppppppppppppppppppppppp");
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTag)
        {
            List<TagVo> tags = tagService.findTagsByArticleId(article.getId());
            articleVo.setTags(tags);
        }
        if (isAuthor)
        {
            SysUser sysUser=sysUserService.findUserById(article.getAuthorId());
            if (sysUser==null)
            {
                sysUser=new SysUser();
                sysUser.setNickname("佚名");
            }
            articleVo.setAuthor(sysUser.getNickname());
        }
        //根据bodyid 查询 body
        if (isBody){
            ArticleBodyVo articleBodyVo=findArticleBody(article.getId());
            articleVo.setBody(articleBodyVo);
        }
        if (isCategory){
            CategoryVo categoryVo=findCategory(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }
    //查找文章所属 类别
    @Autowired
    private CategoryService categoryService;

    private CategoryVo findCategory(Long categoryId) {

        return categoryService.findCategoryById(categoryId);
    }

    //查找文章体
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    private ArticleBodyVo findArticleBody(Long articleId) {
        LambdaQueryWrapper<ArticleBody> wrapper=new LambdaQueryWrapper<>();
        System.out.println(articleId+"bbbbbbbbbbbbbbbb");
        wrapper.eq(ArticleBody::getArticleId,articleId);
        ArticleBody articleBody= articleBodyMapper.selectOne(wrapper);
        System.out.println(articleBody+"88888888888888");
        ArticleBodyVo articleBodyVo=new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        // select  id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        // select  id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList=articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public ArticleVo findArticleById(Long articleId) {
        //System.out.println(articleId+"iiiiiiiiiiiii");
        Article article=articleMapper.selectById(articleId);
        //System.out.println(article+"qqqqqqqqqqqqq");
        //更新 浏览量
        threadService.updateViewCount(articleMapper,article);
        return copy(article,true,true,true,true);
    }

    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser= UserThreadLocal.get();
        Article article=new Article();

        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);
        article.setBodyId(-1L);  //此处 插入文章body 得到bodyid后再 更新bodyid
        //插入后 会生成一个文章id
        articleMapper.insert(article);

        //插入文章对应的所有tag
        List<TagVo> tagVoList=articleParam.getTags();
        if (tagVoList!=null){
            for (TagVo tag: tagVoList) {
                ArticleTag articleTag=new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        //插入文章 content
        ArticleBody articleBody=new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        System.out.println(articleBody+"555555555555555");
        articleBodyMapper.insert(articleBody);

        //更新 article
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo=new ArticleVo();
        //返回id 发布完成后跳转到文章页面
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }


}
