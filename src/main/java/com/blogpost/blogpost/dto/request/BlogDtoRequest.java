package com.blogpost.blogpost.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDtoRequest {

    @NotBlank(message = "The blog must have a title!")
    private String title;

    @NotBlank(message = "The blog must have some content!")
    private String content;

    @NotNull(message = "a user is required")
    private Long userId;

    @NotNull(message = "a forum is required")
    private Long forumId;

    @NotNull(message = "a category is required")
    private Long categoryId;
}
