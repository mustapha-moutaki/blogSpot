package com.blogpost.blogpost.service.impl;

import com.blogpost.blogpost.dto.request.BlogDtoRequest;
import com.blogpost.blogpost.dto.response.BlogDtoResponse;
import com.blogpost.blogpost.exception.ResourceNotFoundException;
import com.blogpost.blogpost.mapper.BlogMapper;
import com.blogpost.blogpost.model.Blog;
import com.blogpost.blogpost.model.Category;
import com.blogpost.blogpost.model.Forum;
import com.blogpost.blogpost.model.User;
import com.blogpost.blogpost.repository.BlogRepository;
import com.blogpost.blogpost.repository.ForumRepository;
import com.blogpost.blogpost.repository.UserRepository;
import com.blogpost.blogpost.repository.CategoryRepository;
import com.blogpost.blogpost.service.BlogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BlogDtoResponse create(BlogDtoRequest dto) {
        Blog blog = blogMapper.toEntity(dto);


        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
            blog.setUser(user);
        } else {
            throw new IllegalArgumentException("Author id is required");
        }


        if (dto.getForumId() != null) {
            Forum forum = forumRepository.findById(dto.getForumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Forum not found with id: " + dto.getForumId()));
            blog.setForum(forum);
        } else {
            throw new IllegalArgumentException("Forum is required");
        }


        if (dto.getCategoryId() != null) {

            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            blog.setCategory(category);
        } else {
            throw new IllegalArgumentException("Category is required");
        }

        Blog savedBlog = blogRepository.save(blog);
        return blogMapper.toDto(savedBlog);
    }

    @Override
    public BlogDtoResponse update(Long id, BlogDtoRequest dto) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id " + id));

        existingBlog.setTitle(dto.getTitle());
        existingBlog.setContent(dto.getContent());

        if (dto.getForumId() != null) {
            Forum forum = forumRepository.findById(dto.getForumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Forum not found"));
            existingBlog.setForum(forum);
        }

        Blog updatedBlog = blogRepository.save(existingBlog);
        return blogMapper.toDto(updatedBlog);
    }

    @Override
    public void delete(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        blogRepository.delete(blog);
    }

    @Override
    public Page<BlogDtoResponse> getAll(Pageable pageable) {
        return blogRepository.findAll(pageable)
                .map(blogMapper::toDto);
    }

    @Override
    public List<BlogDtoResponse> getBlogsByForumId(Long forumId) {
        List<Blog> blogs = blogRepository.findByForumId(forumId);
        return blogs.stream()
                .map(blogMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDtoResponse getById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
        return blogMapper.toDto(blog);
    }
}