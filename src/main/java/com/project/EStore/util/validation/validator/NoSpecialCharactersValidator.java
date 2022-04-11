package com.project.EStore.util.validation.validator;

import com.project.EStore.util.validation.constraint.NoSpecialCharacters;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class NoSpecialCharactersValidator implements ConstraintValidator<NoSpecialCharacters, String> {

    private static final String[] specialCharacters = new String[]{
            "<", ">", "/", "\"", "'", "`", "%", "&", "=", ";", "|"
    };

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String copyValue = String.copyValueOf(value.toCharArray());

        if (!copyValue.equals(value.trim())) {
            return false;
        }

        return Arrays.stream(specialCharacters).noneMatch(value::contains);
    }
}
