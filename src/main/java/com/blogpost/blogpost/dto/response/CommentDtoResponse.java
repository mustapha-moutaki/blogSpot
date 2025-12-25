package com.blogpost.blogpost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoResponse {
    private Long id;
    private String content;

    // IDs for linking
    private Long userId;
    private Long blogId;

    // Extra display fields
    private String authorName;

    // Timestamps from BaseEntity
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}