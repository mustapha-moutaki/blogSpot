package com.blogpost.blogpost.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoResponse {

    private Long id;
    private String name;
    private String description;
    private int blogCount;
}
