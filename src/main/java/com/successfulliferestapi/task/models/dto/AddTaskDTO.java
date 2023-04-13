package com.successfulliferestapi.Task.models.dto;

import com.successfulliferestapi.Task.validations.annotations.ValidTaskPriority;
import com.successfulliferestapi.Task.validations.annotations.ValidTaskStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskDTO {

    @Size(min = 2, max = 145, message = "Title must be between 2 and 145 characters")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Z}]*$", message = "Title can only contain letters, numbers, and characters used in sentences.")
    private String title;

    @Size(min = 5, max = 5000, message = "Description must be between 5 - 5000 characters long.")
    private String description;

    @NotNull(message = "Task status is required!")
    @ValidTaskStatus
    private String status;

    @NotNull(message = "Task priority is required!")
    @ValidTaskPriority
    private String priority;

    private boolean urgent = false;

    private boolean important = false;

    private boolean favorite = false;

    @Positive
    private Long targetId;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}[T ]\\d{2}:\\d{2}:\\d{0,2}$", message = "Please, enter valid start date format!")
    private String startDate;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}[T ]\\d{2}:\\d{2}:\\d{0,2}$", message = "Please, enter valid due date format!")
    private String dueDate;

}
