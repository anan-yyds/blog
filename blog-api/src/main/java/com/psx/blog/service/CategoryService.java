package com.psx.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.psx.blog.common.R;
import com.psx.blog.dao.Category;
import com.psx.blog.vo.CategoryVo;



public interface CategoryService extends IService<Category> {
    CategoryVo findCategoryById(Long categoryId);

    R findAll();

    R findAllDetail();

    R categoryDetailById(Long id);
}
