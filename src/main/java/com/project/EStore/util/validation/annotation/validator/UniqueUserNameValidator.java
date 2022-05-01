package com.project.EStore.util.validation.annotation.validator;

import com.project.EStore.service.domain.user.UserService;
import com.project.EStore.util.validation.annotation.UniqueUserName;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    private final UserService userService;

    public UniqueUserNameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userService.isUsernameUnique(value);
    }
}
