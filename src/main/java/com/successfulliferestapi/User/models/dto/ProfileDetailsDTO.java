package com.successfulliferestapi.User.models.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailsDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String role;
}
