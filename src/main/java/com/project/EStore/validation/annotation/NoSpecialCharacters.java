package com.project.EStore.validation.annotation;

import com.project.EStore.validation.annotation.validator.NoSpecialCharactersValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoSpecialCharactersValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpecialCharacters {
    String message() default "Special characters nor whitespaces allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
