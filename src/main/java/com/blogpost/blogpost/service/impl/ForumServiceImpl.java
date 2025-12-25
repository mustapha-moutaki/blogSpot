package com.blogpost.blogpost.service.impl;

import com.blogpost.blogpost.dto.request.ForumDtoRequest;
import com.blogpost.blogpost.dto.response.ForumDtoResponse;
import com.blogpost.blogpost.exception.ResourceNotFoundException;
import com.blogpost.blogpost.mapper.ForumMapper;
import com.blogpost.blogpost.model.Blog;
import com.blogpost.blogpost.model.Forum;
import com.blogpost.blogpost.model.User;
import com.blogpost.blogpost.repository.BlogRepository;
import com.blogpost.blogpost.repository.ForumRepository;
import com.blogpost.blogpost.repository.UserRepository;
import com.blogpost.blogpost.service.ForumService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final ForumMapper forumMapper;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    // ================= CREATE =================
    @Override
    public ForumDtoResponse create(ForumDtoRequest dto) {
        Forum forum = forumMapper.toEntity(dto);

        // Link owner (Use the single userId field)
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getUserId()));
            forum.setUser(user);
        } else {
            // This is what was causing your 404
            throw new ResourceNotFoundException("Owner userId is required");
        }

        // Link blogs if they exist
        if (dto.getBlogsIds() != null && !dto.getBlogsIds().isEmpty()) {
            var blogs = blogRepository.findAllById(dto.getBlogsIds());
            forum.setBlogs(blogs);
        }

        Forum savedForum = forumRepository.save(forum);
        return forumMapper.toDto(savedForum);
    }
    // ================= UPDATE =================
    @Override
    public ForumDtoResponse update(Long id, ForumDtoRequest dto) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found with id " + id));

        forum.setTitle(dto.getTitle());
        forum.setDescription(dto.getDescription());

        // Update owner if changed
        if (dto.getUserIds() != null && !dto.getUserIds().isEmpty()) {
            Long userId = dto.getUserIds().get(0);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
            forum.setUser(user);
        }

        // Update blogs if changed
        if (dto.getBlogsIds() != null) {
            var blogs = blogRepository.findAllById(dto.getBlogsIds());
            forum.setBlogs(blogs);
        }

        Forum savedForum = forumRepository.save(forum);
        return forumMapper.toDto(savedForum);
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found with id " + id));
        forumRepository.delete(forum);
    }

    // ================= GET ALL =================
    @Override
    public Page<ForumDtoResponse> getAll(Pageable pageable) {
        return forumRepository.findAll(pageable)
                .map(forumMapper::toDto);
    }

    // ================= GET BY ID =================
    @Override
    public ForumDtoResponse getById(Long id) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found with id " + id));

        ForumDtoResponse response = forumMapper.toDto(forum);

        if (forum.getUser() != null) {
            response.setUserIds(List.of(forum.getUser().getId())); // Owner only
        }

        if (forum.getBlogs() != null) {
            response.setBlogsIds(forum.getBlogs().stream().map(Blog::getId).toList());
        }

        return response;
    }
}