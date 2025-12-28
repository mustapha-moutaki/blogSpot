package com.blogpost.blogpost.service;

import com.blogpost.blogpost.dto.request.BlogDtoRequest;
import com.blogpost.blogpost.dto.response.BlogDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    BlogDtoResponse create(BlogDtoRequest dto);
    BlogDtoResponse update(Long id, BlogDtoRequest dto);
    void delete(Long id);
    Page<BlogDtoResponse> getAll(Pageable pageable);
    BlogDtoResponse getById(Long id);
    List<BlogDtoResponse> getBlogsByForumId(Long forumId);
}