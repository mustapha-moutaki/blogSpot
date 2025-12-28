package com.blogpost.blogpost.service.impl;

import com.blogpost.blogpost.dto.request.CategoryDtoRequest;
import com.blogpost.blogpost.dto.response.CategoryDtoResponse;
import com.blogpost.blogpost.mapper.CategoryMapper;
import com.blogpost.blogpost.model.Category;
import com.blogpost.blogpost.repository.CategoryRepository;
import com.blogpost.blogpost.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDtoResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDtoResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryDtoResponse createCategory(CategoryDtoRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}