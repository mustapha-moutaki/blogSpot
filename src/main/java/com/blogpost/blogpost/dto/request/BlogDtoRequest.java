package com.blogpost.blogpost.dto.request;


import com.blogpost.blogpost.model.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDtoRequest {

    @NotBlank(message = "the title is required")
    private String title;

    @NotBlank(message = "the content cannot be empty")
    private String content;

    @NotNull
    private Long userId;
    @NotNull
    private Long forumId;

    @NotBlank(message = "the category is required")
    private Long categoryId;

    private List<Comment> comments = new ArrayList<>();


}
