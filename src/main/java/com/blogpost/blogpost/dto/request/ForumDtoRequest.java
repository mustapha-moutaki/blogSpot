package com.blogpost.blogpost.dto.request;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ForumDtoRequest {

    @NotBlank(message = "the title is required")
    private String title;

    @Lob // for large text
    private String description;


    private List<Long> userIds= new ArrayList<>();

    private List<Long>blogsIds = new ArrayList<>();
}
