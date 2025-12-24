package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.BlogDtoRequest;
import com.blogpost.blogpost.dto.response.BlogDtoResponse;
import com.blogpost.blogpost.model.Blog;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {



    public BlogDtoResponse toResponse(Blog blog) {
        BlogDtoResponse response = new BlogDtoResponse();
        response.setId(blog.getId());
        response.setTitle(blog.getTitle());
        response.setContent(blog.getContent());
        response.setCreatedAt(blog.getCreatedAt());
        response.setUpdatedAt(blog.getUpdatedAt());

        if (blog.getUser() != null) {
            response.setAuthorName(blog.getUser().getFirstName() + " " + blog.getUser().getLastName());
        }

        if (blog.getCategory() != null) {
            response.setCategoryName(blog.getCategory().getName());
        }

        if (blog.getForum() != null) {
            response.setForumTitle(blog.getForum().getTitle());
        }

        return response;
    }
}