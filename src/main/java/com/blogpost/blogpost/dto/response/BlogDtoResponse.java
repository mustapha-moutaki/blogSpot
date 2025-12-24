package com.blogpost.blogpost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BlogDtoResponse {

    private String title;
    private String content;

    private Long userId;
    private Long forumId;

    private Long categoryId;

    private List<Long> commentsIds;

}
