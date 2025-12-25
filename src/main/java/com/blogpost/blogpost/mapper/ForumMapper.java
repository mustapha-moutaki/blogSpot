package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.ForumDtoRequest;
import com.blogpost.blogpost.dto.response.ForumDtoResponse;
import com.blogpost.blogpost.model.Forum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ForumMapper {

    // Ensure the target names match your ForumDtoResponse variables exactly
    @Mapping(target = "userIds", ignore = true)
    @Mapping(target = "blogsIds", ignore = true)
    ForumDtoResponse toDto(Forum forum);

    Forum toEntity(ForumDtoRequest dto);
}