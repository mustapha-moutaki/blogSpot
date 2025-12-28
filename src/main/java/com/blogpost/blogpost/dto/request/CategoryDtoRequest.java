package com.blogpost.blogpost.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoRequest {

        @NotBlank(message = "Category name is required")
        private String name;


        private String description;

}
