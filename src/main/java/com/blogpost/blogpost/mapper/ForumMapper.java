package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.ForumDtoRequest;
import com.blogpost.blogpost.dto.response.ForumDtoResponse;
import com.blogpost.blogpost.model.Forum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ForumMapper {

    @Mapping(target = "userIds", ignore = true)
    @Mapping(target = "blogsIds", ignore = true)
    ForumDtoResponse toDto(Forum forum);

    @Mapping(target = "user", ignore = true) // We will set the user manually in Service
    @Mapping(target = "id", ignore = true)
    Forum toEntity(ForumDtoRequest dto);
}