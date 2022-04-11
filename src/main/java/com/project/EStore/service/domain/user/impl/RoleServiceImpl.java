package com.project.EStore.service.domain.user.impl;

import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.model.entity.user.RoleEntity;
import com.project.EStore.model.service.user.RoleServiceModel;
import com.project.EStore.repository.user.RoleRepository;
import com.project.EStore.service.domain.user.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleServiceModel findByName(RoleEnum role) {
        RoleEntity roleEntity = this.roleRepository.findByRole(role).get();
        return this.modelMapper.map(roleEntity, RoleServiceModel.class);
    }

    @Override
    public void init() {
        if (this.roleRepository.count() > 0) {
            return;
        }

        Arrays.stream(RoleEnum.values()).forEach(roleEnum -> {
            this.roleRepository.save(new RoleEntity().setRole(roleEnum));
        });
    }
}
