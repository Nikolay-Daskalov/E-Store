package com.project.EStore.service.domain.impl;

import com.project.EStore.model.entity.RoleEntity;
import com.project.EStore.model.entity.UserEntity;
import com.project.EStore.model.entity.enums.RoleEnum;
import com.project.EStore.repository.UserRepository;
import com.project.EStore.service.domain.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final static String ADMIN_INIT_USERNAME = "Admin";
    private final static String ADMIN_INIT_PASSWORD = "admin";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
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
    public void init() {
        if (this.userRepository.count() > 0){
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity
                    .setUsername(ADMIN_INIT_USERNAME)
                    .setPassword(this.passwordEncoder.encode(ADMIN_INIT_PASSWORD));

        userEntity.getRoles().add(new RoleEntity().setRole(RoleEnum.ADMIN));
        userEntity.getRoles().add(new RoleEntity().setRole(RoleEnum.EDITOR));
        userEntity.getRoles().add(new RoleEntity().setRole(RoleEnum.USER));

        this.userRepository.save(userEntity);
    }
}
