package com.blogpost.blogpost.service.impl;

import com.blogpost.blogpost.dto.request.CommentDtoRequest;
import com.blogpost.blogpost.dto.response.CommentDtoResponse;
import com.blogpost.blogpost.exception.ResourceNotFoundException;
import com.blogpost.blogpost.mapper.CommentMapper;
import com.blogpost.blogpost.model.Blog;
import com.blogpost.blogpost.model.Comment;
import com.blogpost.blogpost.model.User;
import com.blogpost.blogpost.repository.BlogRepository;
import com.blogpost.blogpost.repository.CommentRepository;
import com.blogpost.blogpost.repository.UserRepository;
import com.blogpost.blogpost.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDtoResponse create(CommentDtoRequest dto) {
        // 1. Map DTO to Entity
        Comment comment = commentMapper.toEntity(dto);

        // 2. Find and link the Author (User)
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        comment.setUser(user);

        // 3. Find and link the Blog
        Blog blog = blogRepository.findById(dto.getBlogId())
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + dto.getBlogId()));
        comment.setBlog(blog);

        // 4. Save and return
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDtoResponse update(Long id, CommentDtoRequest dto) {
        // 1. Find existing comment
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        // 2. Update content
        comment.setContent(dto.getContent());

        // 3. Optional: Update user/blog if your logic allows it (usually just content changes)
        // If needed, repeat the findById logic from create() here.

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    @Override
    public Page<CommentDtoResponse> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(commentMapper::toDto);
    }

    @Override
    public CommentDtoResponse getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        return commentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }
}