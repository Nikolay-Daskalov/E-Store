package com.project.EStore.util.validation.constraint.validator;

import com.project.EStore.util.validation.constraint.NoSpecialCharacters;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

@Component
public class NoSpecialCharactersValidator implements ConstraintValidator<NoSpecialCharacters, String> {

    private static final Set<Character> specialCharacters =
            new HashSet<>(Set.of('<', '>', '\\', '/', '"', '\'', '`', '%', '&', '=', ';', '|'));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.chars().mapToObj(i -> (char) i).noneMatch(character -> {
            if (Character.isWhitespace(character) || specialCharacters.contains(character)) {
                return true;
            }

            return false;
        });
    }
}
