package com.blogpost.blogpost.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoRequest {

    @NotBlank(message = "Comment content cannot be empty")
    @Size(max = 2000, message = "Comment must be under 2000 characters")
    private String content;

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "blogId is required")
    private Long blogId;
}