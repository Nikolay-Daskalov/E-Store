package com.project.EStore.model.binding;

import com.project.EStore.util.validation.constraint.NoSpecialCharacters;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UserBindingModel {

    @NotBlank
    @NoSpecialCharacters
    private String username;
    @NotBlank
    @NoSpecialCharacters
    private String password;
    @NotBlank
    @NoSpecialCharacters
    private String confirmPassword;

    public UserBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
