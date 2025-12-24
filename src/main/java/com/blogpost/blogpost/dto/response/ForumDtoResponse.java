package com.blogpost.blogpost.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ForumDtoResponse {

    private Long id;

    private String title;
    private String description;

    private List<Long> userIds;
    private List<Long>blogsIds;
}
