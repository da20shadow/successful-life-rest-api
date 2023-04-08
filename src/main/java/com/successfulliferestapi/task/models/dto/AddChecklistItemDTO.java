package com.successfulliferestapi.Task.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddChecklistItemDTO {

    @NotNull(message = "Title can not be empty!")
    @Size(min = 5,max = 145,message = "Title must be between 5 and 145 characters long!")
    private String title;

    private final boolean completed = false;

}
