package com.successfulliferestapi.Task.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskSuccessResponseDTO {
    private String message;
    private TaskDTO task;
}
