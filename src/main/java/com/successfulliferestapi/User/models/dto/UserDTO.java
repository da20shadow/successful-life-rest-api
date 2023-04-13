package com.successfulliferestapi.User.models.dto;

import com.successfulliferestapi.User.models.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
    private String firstName;
    private String email;
    private String username;
    private boolean banned;
    private UserRole role;
}
