package com.psx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.psx.blog.common.R;
import com.psx.blog.dao.Tag;
import com.psx.blog.mapper.TagMapper;
import com.psx.blog.service.TagService;
import com.psx.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);

        return copyList(tags);
    }

    @Override
    public List<Tag> hots(int limit) {
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);

        if(CollectionUtils.isEmpty(tagIds)){
            return Collections.emptyList();
        }
        List<Tag> tags = tagMapper.findTagsByTagIds(tagIds);
        return tags;
    }

    @Override
    public R findAll() {
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();
        lqw.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(lqw);
        return R.success(copyList(tags));
    }

    @Override
    public R findAllDetail() {
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();
        List<Tag> tags = tagMapper.selectList(lqw);
        return R.success(copyList(tags));
    }

    @Override
    public R findTagDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return R.success(copy(tag));
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        tagList.forEach(item->tagVoList.add(copy(item)));
        return tagVoList;
    }
}
