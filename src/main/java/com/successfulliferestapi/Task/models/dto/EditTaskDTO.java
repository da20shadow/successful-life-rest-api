package com.successfulliferestapi.Task.models.dto;

import com.successfulliferestapi.Task.models.enums.TaskPriority;
import com.successfulliferestapi.Task.models.enums.TaskStatus;
import com.successfulliferestapi.Task.validations.annotations.ValidTaskPriority;
import com.successfulliferestapi.Task.validations.annotations.ValidTaskStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditTaskDTO {
    @Size(min = 2, max = 145, message = "Title must be between 2 and 145 characters")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Z}]*$", message = "Title can only contain letters, numbers, and characters used in sentences.")
    private String title;

    @Size(min = 5, max = 5000, message = "Description must be between 5 - 5000 characters long.")
    private String description;

    @ValidTaskStatus
    private String status;

    @ValidTaskPriority
    private String priority;

    private boolean urgent;

    private boolean important;

    private String startDate;

    private String dueDate;

}