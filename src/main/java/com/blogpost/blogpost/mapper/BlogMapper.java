package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.BlogDtoRequest;
import com.blogpost.blogpost.dto.response.BlogDtoResponse;
import com.blogpost.blogpost.model.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "forumId", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "commentsIds", ignore = true)
    BlogDtoResponse toDto(Blog blog);

    Blog toEntity(BlogDtoRequest dto);
}
