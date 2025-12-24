package com.blogpost.blogpost.service;

import com.blogpost.blogpost.dto.request.ForumDtoRequest;
import com.blogpost.blogpost.dto.response.ForumDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ForumService {

    ForumDtoResponse create(ForumDtoRequest dto);
    ForumDtoResponse update(Long id, ForumDtoRequest dto);
    void delete (Long id);
    Page<ForumDtoResponse>getAll(Pageable pageable);
    ForumDtoResponse getById(Long id);
}
