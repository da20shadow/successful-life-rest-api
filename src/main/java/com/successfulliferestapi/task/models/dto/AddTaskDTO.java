package com.successfulliferestapi.Task.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskDTO {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 145, message = "Title must be between 2 and 145 characters")
    private String title;

    private String description;
    private String status;
    private String priority;
    private boolean urgent = false;
    private boolean important = false;
    private boolean favorite = false;
    private Long targetId = null;
    private String startDate;
    private String dueDate;

}
