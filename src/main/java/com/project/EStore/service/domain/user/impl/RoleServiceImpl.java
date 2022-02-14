package com.project.EStore.service.domain.user.impl;

import com.project.EStore.repository.user.RoleRepository;
import com.project.EStore.service.domain.user.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
