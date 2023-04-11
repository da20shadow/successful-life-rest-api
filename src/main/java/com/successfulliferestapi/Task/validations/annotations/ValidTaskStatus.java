package com.successfulliferestapi.Task.validations.annotations;

import com.successfulliferestapi.Task.validations.validators.TaskStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskStatusValidator.class)
public @interface ValidTaskStatus {
    String message() default "Invalid task status.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

