package com.project.EStore.service.user;

import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.service.init.Init;

import java.util.List;
import java.util.Set;

public interface UserService extends Init {

    void add(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsername(String username);

    void deleteUserByUsername(String username);

    List<UserServiceModel> getAllUsers();

    boolean isUserExists(String username);

    void addRolesToUser(String username, Set<RoleEnum> roles);
}
