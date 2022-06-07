package com.psx.blog.controller;

import com.psx.blog.common.R;
import com.psx.blog.dao.Tag;
import com.psx.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
@Slf4j
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public R<List<Tag>> hot(){
        int limit = 6;
        List<Tag> hots = tagService.hots(limit);
        log.info("最热标签是{}",hots);
        return R.success(hots);
    }
    @GetMapping
    public R findAll(){
        return tagService.findAll();
    }
    @GetMapping("detail")
    public R findAllDetail(){
        return tagService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    public R findTagDetailById(@PathVariable Long id){
        return tagService.findTagDetailById(id);
    }

}
