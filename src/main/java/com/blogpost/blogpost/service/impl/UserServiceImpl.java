package com.blogpost.blogpost.service.impl;

import com.blogpost.blogpost.dto.request.UserDtoRequest;
import com.blogpost.blogpost.dto.response.UserDtoResponse;
import com.blogpost.blogpost.exception.EmailAlreadyExistsException;
import com.blogpost.blogpost.exception.ResourceNotFoundException;
import com.blogpost.blogpost.mapper.UserMapper;
import com.blogpost.blogpost.model.Blog;
import com.blogpost.blogpost.model.Forum;
import com.blogpost.blogpost.model.User;
import com.blogpost.blogpost.repository.UserRepository;
import com.blogpost.blogpost.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // ================= CREATE =================
    @Override
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {

        // 1. check email uniqueness
        if (userRepository.findByEmail(userDtoRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // 2. map dto -> entity
        User user = userMapper.toEntity(userDtoRequest);

        // 3. save
        User savedUser = userRepository.save(user);

        // 4. map entity -> dto
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDtoResponse updateUser(Long id, UserDtoRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Only update fields from DTO
        userMapper.updateUserFromDto(dto, user);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    // ================= DELETE =================
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id)
                );

        userRepository.delete(user);
    }

    // ================= GET ALL =================
    @Override
    public Page<UserDtoResponse> getAll(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    // ================= GET BY ID =================
    @Override
    public UserDtoResponse getById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id)
                );

        UserDtoResponse response = userMapper.toDto(user);

        fillRelationsIds(user, response);

        return response;
    }

    // ================= PRIVATE HELPERS =================
    private void fillRelationsIds(User user, UserDtoResponse response) {

        if (user.getForums() != null) {
            List<Long> forumsIds = user.getForums()
                    .stream()
                    .map(Forum::getId)
                    .toList();
            response.setForumsIds(forumsIds);
        }

        if (user.getBlogs() != null) {
            List<Long> blogsIds = user.getBlogs()
                    .stream()
                    .map(Blog::getId)
                    .toList();
            response.setBlogsIds(blogsIds);
        }
    }
}
