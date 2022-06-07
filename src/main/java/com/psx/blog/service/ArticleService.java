package com.psx.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.psx.blog.common.R;
import com.psx.blog.dao.Article;
import com.psx.blog.vo.param.ArticleParam;
import com.psx.blog.vo.param.PageParam;

import java.util.List;

public interface ArticleService extends IService<Article> {

    R page(PageParam params);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    List hotArticle(int limit);

    List newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    List listArchives();

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    R findArticleById(Long articleId);

    R publish(ArticleParam articleParam);

}
