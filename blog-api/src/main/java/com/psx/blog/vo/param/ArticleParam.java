package com.psx.blog.vo.param;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.psx.blog.vo.CategoryVo;
import com.psx.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {
    
    private Long id;	
	
    private ArticleBodyParam body;	
	
    private CategoryVo category;
	
    private String summary;	
	
    private List<TagVo> tags;
	
    private String title;	
}	
