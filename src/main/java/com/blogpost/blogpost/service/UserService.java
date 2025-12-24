package com.blogpost.blogpost.service;


import com.blogpost.blogpost.dto.request.UserDtoRequest;
import com.blogpost.blogpost.dto.response.UserDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDtoResponse createUser(UserDtoRequest userDtoRequest);
    UserDtoResponse updateUser(Long id, UserDtoRequest dto);
    void deleteUser(Long id);
    Page<UserDtoResponse> getAll(Pageable pageable);
    UserDtoResponse getById(Long id);
}
