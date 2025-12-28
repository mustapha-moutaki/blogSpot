package com.blogpost.blogpost.controller;

import com.blogpost.blogpost.dto.request.CommentDtoRequest;
import com.blogpost.blogpost.dto.response.CommentDtoResponse;
import com.blogpost.blogpost.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDtoResponse> create(@Valid @RequestBody CommentDtoRequest request) {
        return new ResponseEntity<>(commentService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CommentDtoResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(commentService.getAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
