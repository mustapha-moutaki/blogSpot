package com.blogpost.blogpost.service;

import com.blogpost.blogpost.dto.request.CategoryDtoRequest;
import com.blogpost.blogpost.dto.response.CategoryDtoResponse;

import java.util.List;

public interface CategoryService {
    public List<CategoryDtoResponse> getAllCategories();
    public CategoryDtoResponse getCategoryById(Long id);
    public CategoryDtoResponse createCategory(CategoryDtoRequest request);
    public void deleteCategory(Long id);
}
