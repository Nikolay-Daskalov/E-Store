package com.project.EStore.service.domain.user;

import com.project.EStore.service.domain.init.Init;

public interface UserService extends Init {

    boolean isUsernameUnique(String username);
}
