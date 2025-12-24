package com.blogpost.blogpost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDtoResponse {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String categoryName;
    private String forumTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
