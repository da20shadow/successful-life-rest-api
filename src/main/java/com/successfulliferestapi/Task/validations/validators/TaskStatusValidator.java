package com.successfulliferestapi.Task.validations.validators;

import com.successfulliferestapi.Task.models.enums.TaskStatus;
import com.successfulliferestapi.Task.validations.annotations.ValidTaskStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, String> {

    @Override
    public void initialize(ValidTaskStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are allowed
        }

        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.name().equals(value)) {
                return true; // found a matching enum value
            }
        }

        return false; // no matching enum value found
    }
}

