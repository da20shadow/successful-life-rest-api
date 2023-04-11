package com.successfulliferestapi.User.models.dto;

import com.successfulliferestapi.User.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginSuccessResponseDTO {
    private String message;
    private String token;
    private String firstName;
    private String email;
    private String role;
}
