package com.blogpost.blogpost.service;

import com.blogpost.blogpost.dto.request.BlogDtoRequest;
import com.blogpost.blogpost.dto.response.BlogDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    BlogDtoResponse create(BlogDtoRequest dto);
    BlogDtoResponse update(Long id, BlogDtoRequest dto);
    void delete(Long id);
    Page<BlogDtoResponse> getAll(Pageable pageable);
    BlogDtoResponse getById(Long id);
}