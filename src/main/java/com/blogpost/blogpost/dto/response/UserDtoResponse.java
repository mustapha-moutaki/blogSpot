package com.blogpost.blogpost.dto.response;

import com.blogpost.blogpost.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {

    private Long id;
    private String firstName;
    private String lastName;

    private String email;
    private UserRole role;

}
