package com.blogpost.blogpost.controller;

import com.blogpost.blogpost.dto.request.ForumDtoRequest;
import com.blogpost.blogpost.dto.response.ForumDtoResponse;
import com.blogpost.blogpost.service.ForumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/forums")
public class ForumController {

    private final ForumService forumService;

    // CREATE FORUM
    @PostMapping
    public ResponseEntity<ForumDtoResponse> create(@Valid @RequestBody ForumDtoRequest request) {
        ForumDtoResponse response = forumService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL FORUMS (PAGINATION)
    @GetMapping
    public ResponseEntity<Page<ForumDtoResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(forumService.getAll(pageable));
    }

    // GET FORUM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ForumDtoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(forumService.getById(id));
    }

    // UPDATE FORUM
    @PutMapping("/{id}")
    public ResponseEntity<ForumDtoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ForumDtoRequest request
    ) {
        return ResponseEntity.ok(forumService.update(id, request));
    }

    // DELETE FORUM
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        forumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}