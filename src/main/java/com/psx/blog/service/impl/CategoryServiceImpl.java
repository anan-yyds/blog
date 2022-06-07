package com.psx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.psx.blog.common.R;
import com.psx.blog.dao.Category;
import com.psx.blog.mapper.CategoryMapper;
import com.psx.blog.service.CategoryService;
import com.psx.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public R findAll() {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(lqw);
        //和页面交互的对象
        return R.success(copyList(categories));
    }

    @Override
    public R findAllDetail() {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(lqw);
        //和页面交互的对象
        return R.success(copyList(categories));
    }

    @Override
    public R categoryDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        return R.success(copy(category));
    }

    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
    public List<CategoryVo> copyList(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

}
