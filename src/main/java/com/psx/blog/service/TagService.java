package com.psx.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.psx.blog.common.R;
import com.psx.blog.dao.Tag;
import com.psx.blog.vo.TagVo;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagVo> findTagsByArticleId(Long articleId);

    List<Tag> hots(int limit);

    R findAll();

    R findAllDetail();

    R findTagDetailById(Long id);
}
