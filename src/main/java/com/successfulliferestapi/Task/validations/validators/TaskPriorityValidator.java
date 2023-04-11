package com.successfulliferestapi.Task.validations.validators;

import com.successfulliferestapi.Task.models.enums.TaskPriority;
import com.successfulliferestapi.Task.validations.annotations.ValidTaskPriority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskPriorityValidator implements ConstraintValidator<ValidTaskPriority,String> {

    @Override
    public void initialize(ValidTaskPriority constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are allowed
        }

        for (TaskPriority taskPriority : TaskPriority.values()) {
            if (taskPriority.name().equals(value)) {
                return true; // found a matching enum value
            }
        }

        return false; // no matching enum value found
    }
}
