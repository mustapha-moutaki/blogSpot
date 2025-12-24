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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {

        // 1. check email
        if (userRepository.findByEmail(userDtoRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }



        // 2. map dto -> entity
        User user = userMapper.toEntity(userDtoRequest);

        // 3. save
        User savedUser = userRepository.save(user);

        // 4. map entity -> response
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDtoResponse updateUser(Long id, UserDtoRequest dto) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("\"User not found with id \" + id)"));

        userMapper.toDto(user);

        User savedUSer = userRepository.save(user);

        return userMapper.toDto(savedUSer);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user notfind with id:"+id));

        userRepository.delete(user);

    }

    @Override
    public Page<UserDtoResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public UserDtoResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id " + id));

        UserDtoResponse response = userMapper.toDto(user);

        if(user.getForums() != null){
            List<Long> formusIds = user.getForums().stream()
                    .map(Forum::getId)
                    .toList();
        response.setForumsIds(formusIds);
        }

        if(user.getBlogs() != null){
            List<Long>blogsIds = user.getBlogs()
                    .stream().map(Blog::getId)
                    .toList();
            response.setBlogsIds(blogsIds);
        }

        return response;

    }
}
