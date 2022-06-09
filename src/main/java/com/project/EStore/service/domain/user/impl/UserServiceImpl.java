package com.project.EStore.service.domain.user.impl;

import com.project.EStore.config.DatabaseConfig;
import com.project.EStore.model.entity.user.RoleEntity;
import com.project.EStore.model.entity.user.UserEntity;
import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.model.service.user.RoleServiceModel;
import com.project.EStore.model.service.user.UserServiceModel;
import com.project.EStore.repository.user.UserRepository;
import com.project.EStore.service.domain.order.OrderService;
import com.project.EStore.service.domain.user.RoleService;
import com.project.EStore.service.domain.user.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final DatabaseConfig databaseConfig;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(DatabaseConfig databaseConfig, UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.databaseConfig = databaseConfig;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity isFound = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return mapUserEntityToUserDetails(isFound);
    }

    private static UserDetails mapUserEntityToUserDetails(UserEntity userEntity) {
        Set<SimpleGrantedAuthority> roles = userEntity.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                .collect(Collectors.toSet());

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                roles
        );
    }

    @Override
    @Transactional
    public void init() {
        if (this.userRepository.count() > 0) {
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity
                .setUsername(this.databaseConfig.getAdminUsername())
                .setPassword(this.passwordEncoder.encode(this.databaseConfig.getAdminPassword()));

        Arrays.stream(RoleEnum.values()).forEach(role -> {
            userEntity.getRoles().add(this.modelMapper.map(this.roleService.findByName(role), RoleEntity.class));
        });

        this.userRepository.save(userEntity);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        return this.userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public void add(UserServiceModel userServiceModel) {
        UserEntity user = this.modelMapper.map(userServiceModel, UserEntity.class);
        RoleServiceModel roleServiceModel = this.roleService.findByName(RoleEnum.USER);

        RoleEntity roleEntity = this.modelMapper.map(roleServiceModel, RoleEntity.class);

        user.getRoles().add(roleEntity);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        this.userRepository.save(user);

        LOGGER.info(String.format("User successfully registered { %s }", user.getUsername()));
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);

        return userEntity == null ? null : this.modelMapper.map(userEntity, UserServiceModel.class);
    }
}
