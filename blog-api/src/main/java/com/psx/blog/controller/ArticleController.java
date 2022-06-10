package com.psx.blog.controller;

import com.psx.blog.common.R;
import com.psx.blog.common.aop.LogAnnotation;
import com.psx.blog.common.cache.Cache;
import com.psx.blog.service.ArticleService;
import com.psx.blog.vo.param.ArticleParam;
import com.psx.blog.vo.param.PageParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("articles")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /**
     * 首页，文章列表
     * @return
     */
    @PostMapping
    //加上此注解 代表对此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    public R page(@RequestBody PageParam params){
        log.info("获取页面成功");
        return articleService.page(params);
    }
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public R<List> hotArticle(){
        int limit = 5;
        List list = articleService.hotArticle(limit);
        return R.success(list);
    }
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000,name = "news_article")
    public R<List> newArticle(){
        int limit = 5;
        List list = articleService.newArticle(limit);
        return R.success(list);
    }
    @PostMapping("listArchives")
    @Cache(expire = 5 * 60 * 1000,name = "list_article")
    public R<List> listArchives(){
        List list = articleService.listArchives();
        return R.success(list);
    }
    @PostMapping("view/{id}")
    public R findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }
    @PostMapping("publish")
    public R publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
