package com.successfulliferestapi.User.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequestDTO {

    @NotBlank(message = "Old password is required!")
    @Size(min = 6, max = 145, message = "Old password must be between 6 and 145 characters long!")
    private String oldPassword;

    @NotBlank(message = "New Password is required!")
    @Size(min = 6, max = 145, message = "New password must be between 6 and 145 characters long!")
    private String newPassword;

}
