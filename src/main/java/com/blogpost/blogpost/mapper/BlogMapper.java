package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.BlogDtoRequest;
import com.blogpost.blogpost.dto.response.BlogDtoResponse;
import com.blogpost.blogpost.model.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

// unmappedTargetPolicy = ReportingPolicy.IGNORE hides the "Unmapped target properties" warnings
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BlogMapper {

    @Mapping(source = "user.firstName", target = "authorName")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "forum.title", target = "forumTitle")
    BlogDtoResponse toDto(Blog blog);

    Blog toEntity(BlogDtoRequest dto);
}