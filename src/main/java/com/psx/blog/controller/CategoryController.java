package com.psx.blog.controller;

import com.psx.blog.common.R;
import com.psx.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public R categories(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public R detail(){
        return categoryService.findAllDetail();
    }
    @GetMapping("detail/{id}")
    public R categoryDetailById(@PathVariable Long id){
        return categoryService.categoryDetailById(id);
    }

}
