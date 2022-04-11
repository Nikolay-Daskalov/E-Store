package com.project.EStore.util.validation.constraint;

import com.project.EStore.util.validation.validator.UniqueUserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserName {

    String message() default "Username is taken.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
