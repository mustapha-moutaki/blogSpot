package com.blogpost.blogpost.controller;

import com.blogpost.blogpost.dto.request.BlogDtoRequest;
import com.blogpost.blogpost.dto.response.BlogDtoResponse;
import com.blogpost.blogpost.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    // CREATE BLOG
    @PostMapping
    public ResponseEntity<BlogDtoResponse> create(@Valid @RequestBody BlogDtoRequest request) {
        BlogDtoResponse response = blogService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL BLOGS (WITH PAGINATION)
    @GetMapping
    public ResponseEntity<Page<BlogDtoResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(blogService.getAll(pageable));
    }

    // GET BLOG BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BlogDtoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getById(id));
    }

    // UPDATE BLOG
    @PutMapping("/{id}")
    public ResponseEntity<BlogDtoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BlogDtoRequest request
    ) {
        return ResponseEntity.ok(blogService.update(id, request));
    }

    // DELETE BLOG
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}