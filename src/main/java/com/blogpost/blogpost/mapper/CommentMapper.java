package com.blogpost.blogpost.mapper;

import com.blogpost.blogpost.dto.request.CommentDtoRequest;
import com.blogpost.blogpost.dto.response.CommentDtoResponse;
import com.blogpost.blogpost.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "blog.id", target = "blogId")
    @Mapping(target = "authorName", expression = "java(comment.getUser().getFirstName() + \" \" + comment.getUser().getLastName())")
    CommentDtoResponse toDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true) // Set manually in service
    @Mapping(target = "blog", ignore = true) // Set manually in service
    Comment toEntity(CommentDtoRequest dto);
}