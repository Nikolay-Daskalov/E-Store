package com.project.EStore.service.domain.impl;

import com.project.EStore.repository.RoleRepository;
import com.project.EStore.service.domain.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
