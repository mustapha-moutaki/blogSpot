package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.CategoryDtoRequest;
import com.blogpost.blogpost.dto.response.CategoryDtoResponse;
import com.blogpost.blogpost.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDtoRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    public CategoryDtoResponse toResponse(Category category) {
        CategoryDtoResponse response = new CategoryDtoResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        // Null check for blogs list in case it's lazy loaded or null
        response.setBlogCount(category.getBlogs() != null ? category.getBlogs().size() : 0);
        return response;
    }
}