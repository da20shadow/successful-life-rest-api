package com.successfulliferestapi.Task.validations.annotations;

import com.successfulliferestapi.Task.validations.validators.TaskPriorityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskPriorityValidator.class)
public @interface ValidTaskPriority {
    String message() default "Invalid task priority.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
