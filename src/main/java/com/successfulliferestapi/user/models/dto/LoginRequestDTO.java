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
public class LoginRequestDTO {

    @NotBlank
    @Size(min = 9, max = 145)
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+[@][a-z]{2,}[.][a-z]{2,}$")
    private String email;

    @NotBlank
    @Size(min = 6, max = 145)
    private String password;

}
