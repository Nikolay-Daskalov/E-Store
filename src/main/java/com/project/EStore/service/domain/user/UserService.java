package com.project.EStore.service.domain.user;

import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.service.domain.init.Init;

import java.util.List;

public interface UserService extends Init {

    boolean isUsernameUnique(String username);

    void add(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    void deleteUserByUsername(String username);

    List<UserServiceModel> getAllUsers();
}
