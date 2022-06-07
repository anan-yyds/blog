package com.psx.blog.service;

import com.psx.blog.common.R;
import com.psx.blog.vo.param.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id查询所有评论
     * @param id
     * @return
     */
    R commentsByArticleId(Long id);

    R comment(CommentParam commentParam);

}
