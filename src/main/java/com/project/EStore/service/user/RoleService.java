package com.project.EStore.service.user;

import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.model.service.user.RoleServiceModel;
import com.project.EStore.service.init.Init;

public interface RoleService extends Init {

    RoleServiceModel findByName(RoleEnum role);
}
