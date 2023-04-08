package com.successfulliferestapi.User.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeEmailRequestDTO {

    @NotBlank(message = "Email is required!")
    @Size(min = 9, max = 145, message = "Email must be between 9 and 145 characters long!")
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+[@][a-z]{2,}[.][a-z]{2,}$", message = "Please enter valid email format!")
    private String email;

}
