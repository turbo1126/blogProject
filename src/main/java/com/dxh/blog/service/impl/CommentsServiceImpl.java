package com.dxh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dxh.blog.dao.mapper.CommentsMapper;
import com.dxh.blog.dao.pojo.Comment;
import com.dxh.blog.dao.pojo.SysUser;
import com.dxh.blog.service.CommentsService;
import com.dxh.blog.service.SysUserService;
import com.dxh.blog.utils.UserThreadLocal;
import com.dxh.blog.vo.CommentVo;
import com.dxh.blog.vo.Result;
import com.dxh.blog.vo.UserVo;
import com.dxh.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private SysUserService sysUserService;


    //查询文章下的所有评论
    @Override
    public Result commentsByArticleId(Long articleId) {

        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments=commentsMapper.selectList(queryWrapper);

        return Result.success(copyList(comments));
    }

    //通过 parentId 查找子评论
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments=commentsMapper.selectList(queryWrapper);
        return copyList(comments);
    }

    private List<CommentVo> copyList(List<Comment> commentList) {
        List<CommentVo> commentVoList=new ArrayList<>();
        for (Comment coment: commentList) {
            commentVoList.add(copy(coment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //查找评论所属用户信息
        Long authorId=comment.getAuthorId();
        UserVo userVo=this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //查找评论的评论
        List<CommentVo> commentVoList=findCommentsByParentId(comment.getId());
        //如果是子评论 则加入评论对象
        if (comment.getLevel()>1){
            Long toUid=comment.getToUid();
            UserVo touserVo=this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(touserVo);
        }
        return commentVo;
    }
    //评论功能
    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser= UserThreadLocal.get();
        Comment comment=new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent=commentParam.getParent();
        if (parent==null || parent==0){
            comment.setLevel(1);
        }else {
            comment.setLevel(2);
        }
        comment.setParentId(parent==null ? 0 :parent);
        Long toUserId=commentParam.getToUserId();
        comment.setToUid(toUserId==null ? 0:toUserId);
        commentsMapper.insert(comment);
        return Result.success(null);
    }

}
