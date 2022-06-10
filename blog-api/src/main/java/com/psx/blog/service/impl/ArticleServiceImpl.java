package com.psx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.psx.blog.common.R;
import com.psx.blog.dao.Article;
import com.psx.blog.dao.ArticleBody;
import com.psx.blog.dao.ArticleTag;
import com.psx.blog.dao.SysUser;
import com.psx.blog.dao.dos.Archives;
import com.psx.blog.mapper.ArticleBodyMapper;
import com.psx.blog.mapper.ArticleMapper;
import com.psx.blog.mapper.ArticleTagMapper;
import com.psx.blog.service.*;
import com.psx.blog.utils.UserThreadLocal;
import com.psx.blog.vo.ArticleBodyVo;
import com.psx.blog.vo.ArticleVo;
import com.psx.blog.vo.TagVo;
import com.psx.blog.vo.param.ArticleParam;
import com.psx.blog.vo.param.PageParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

   /* @Override
    public List page(PageParam params) {

        Page<Article> pageInfo = new Page<>(params.getPage(),params.getPageSize());

        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        if (params.getCategoryId()!=null){
            lqw.eq(Article::getCategoryId,params.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if (params.getTagId()!=null){
            //加入标签条件查询
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleTag::getId,params.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
            articleTags.forEach(articleTag->articleIdList.add(articleTag.getArticleId()));
            if(articleIdList.size()>0){
                lqw.in(Article::getId,articleIdList);
            }
        }
        lqw.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(pageInfo, lqw);

        List<Article> records = articlePage.getRecords();

        List<ArticleVo> articleVoList = copyList(records,true,true);


        return articleVoList;

    }*/

    @Override
    public R page(PageParam params) {
        Page<Article> pageInfo = new Page<>(params.getPage(),params.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(pageInfo,
                                                                params.getCategoryId(),
                                                                params.getTagId(),
                                                                params.getYear(),
                                                                params.getMonth());
        List<Article> records = articleIPage.getRecords();
        return R.success(copyList(records,true,true));

    }

    @Override
    public List hotArticle(int limit) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(Article::getViewCounts);
        lqw.select(Article::getId,Article::getTitle);
        lqw.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(lqw);
        List<ArticleVo> articleVoList = copyList(articles, true, true);
        return articleVoList;
    }

    @Override
    public List newArticle(int limit) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(Article::getCreateDate);
        lqw.select(Article::getId,Article::getTitle);
        lqw.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(lqw);
        List<ArticleVo> articleVoList = copyList(articles, true, true);
        return articleVoList;
    }

    @Override
    public List listArchives() {
        List<Archives> list = articleMapper.listArchives();
        return list;
    }
    @Autowired
    private ThreadService threadService;

    @Override
    public R findArticleById(Long articleId) {
        //根据id查询文章信息
        Article article = articleMapper.selectById(articleId);
        //根据bodyId和categoryId去做关联查询
        ArticleVo articleVo = copy(article, true, true,true,true);
        //查看完文章后新增阅读数
        threadService.updateArticleViewCount(articleMapper,article);
        return R.success(articleVo);
    }

    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    public R publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1.发布文章 目的 构建Article对象
         *2.作者id 当前登录的用户
         * 3.标签 要将标签加入到关联列表当中
         * 4.body内容存储 article bodyId
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        //插入之后会生成文章id
        articleMapper.insert(article);
        List<TagVo> tags = articleParam.getTags();
        if(tags != null){
            for(TagVo tag : tags){
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return R.success(map);
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        ArrayList<ArticleVo> articleVo = new ArrayList<>();
        records.forEach(item->articleVo.add(copy(item,isTag,isAuthor,false,false)));
        return articleVo;
    }
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        ArrayList<ArticleVo> articleVo = new ArrayList<>();
        records.forEach(item->articleVo.add(copy(item,isTag,isAuthor,isBody,isCategory)));
        return articleVo;
    }
    @Autowired
    private CategoryService categoryService;

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if(isTag){
            Long authorId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(authorId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if(isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }
    @Autowired
    private ArticleBodyMapper articleBodyMapper;


    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
