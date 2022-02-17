package com.project.EStore.service.validation.validator;

import com.project.EStore.service.domain.user.UserService;
import com.project.EStore.service.validation.constraint.UniqueUserName;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
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
