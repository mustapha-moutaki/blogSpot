package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.ForumDtoRequest;
import com.blogpost.blogpost.dto.response.ForumDtoResponse;
import com.blogpost.blogpost.model.Forum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForumMapper {

    @Mapping(target = "userIds", ignore = true)
    @Mapping(target = "blogsIds", ignore = true)
    ForumDtoResponse toDto(Forum dto);


    Forum toEntity(ForumDtoRequest dto);
}
