package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.UserDtoRequest;
import com.blogpost.blogpost.dto.response.UserDtoResponse;
import com.blogpost.blogpost.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "forumsIds", ignore = true)
    @Mapping(target = "blogsIds", ignore = true)
    UserDtoResponse toDto(User user);

    User toEntity(UserDtoRequest userDtoRequest);
}
