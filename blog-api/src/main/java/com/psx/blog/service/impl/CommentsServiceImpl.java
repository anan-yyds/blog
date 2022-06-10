package com.psx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.psx.blog.common.R;
import com.psx.blog.dao.Comment;
import com.psx.blog.dao.SysUser;
import com.psx.blog.mapper.CommentMapper;
import com.psx.blog.service.CommentsService;
import com.psx.blog.service.SysUserService;
import com.psx.blog.utils.UserThreadLocal;
import com.psx.blog.vo.param.CommentParam;
import com.psx.blog.vo.CommentVo;
import com.psx.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public R commentsByArticleId(Long id) {
        //根据文章id查询评论列表从comment表中查询
        //根据作者id查询作者信息
        //判断 如果level = 1 要去查询它有没有子评论
        //如果有 根据评论id 进行查询
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getArticleId,id);
        lqw.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(lqw);
        List<CommentVo> commentVoList = copyList(comments);
        return R.success(commentVoList);
    }

    @Override
    public R comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return R.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        comments.forEach(item->commentVoList.add(copy(item)));
        return commentVoList;
    }
    private CommentVo copy(Comment comment){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if(1==level){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //toUser给谁评论
        if(level>1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Comment::getParentId,id);
        lqw.eq(Comment::getLevel,2);
        return copyList(commentMapper.selectList(lqw));
    }
}
