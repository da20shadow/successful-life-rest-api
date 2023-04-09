package com.successfulliferestapi.Task.models.dto;

import com.successfulliferestapi.Task.models.enums.TaskPriority;
import com.successfulliferestapi.Task.models.enums.TaskStatus;
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
    private String title;

    @Size(max = 1024, message = "Description cannot exceed 1024 characters")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private boolean urgent;

    private boolean important;

    private String startDate;

    private String dueDate;

}