package com.psx.blog.controller;


import com.psx.blog.common.R;
import com.psx.blog.service.CommentsService;
import com.psx.blog.vo.param.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class commentsController {

    @Autowired
    private CommentsService commentsService;
    @GetMapping("article/{id}")
    public R comments(@PathVariable Long id){
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    public R comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
