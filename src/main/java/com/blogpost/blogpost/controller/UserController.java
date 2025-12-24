package com.blogpost.blogpost.controller;

import com.blogpost.blogpost.dto.request.UserDtoRequest;
import com.blogpost.blogpost.dto.response.UserDtoResponse;
import com.blogpost.blogpost.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserDtoResponse> createUser(
            @Valid @RequestBody UserDtoRequest request
    ) {
        UserDtoResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    // GET ALL USERS (PAGINATION)
    @GetMapping
    public ResponseEntity<Page<UserDtoResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDtoRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
