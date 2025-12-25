package com.blogpost.blogpost.service;

import com.blogpost.blogpost.dto.request.CommentDtoRequest;
import com.blogpost.blogpost.dto.response.CommentDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDtoResponse create(CommentDtoRequest dto);
    CommentDtoResponse update(Long id, CommentDtoRequest dto);
    Page<CommentDtoResponse> getAll(Pageable pageable);
    void delete(Long id);
    CommentDtoResponse getById(Long id);
}
