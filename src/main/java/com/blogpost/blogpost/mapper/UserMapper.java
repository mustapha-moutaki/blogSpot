package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.UserDtoRequest;
import com.blogpost.blogpost.dto.response.UserDtoResponse;
import com.blogpost.blogpost.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "forumsIds", ignore = true)
    @Mapping(target = "blogsIds", ignore = true)
    UserDtoResponse toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "forums", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    @Mapping(target = "comments", ignore = true)
    User toEntity(UserDtoRequest userDtoRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "forums", ignore = true)
    @Mapping(target = "blogs", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void updateUserFromDto(UserDtoRequest dto, @MappingTarget User user);
}